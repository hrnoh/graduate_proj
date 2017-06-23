#-*- coding: utf-8 -*-
from pathlib import Path
import websocket
import threading
import json
import time
import sys
import uuid
import datetime
import face_recognition
import picamera
import numpy as np
import RPi.GPIO as GPIO

class Client:
    def __init__(self):
        self.mac = get_mac()
        self.connectionCount = 0
        self.evt = threading.Event()

        # 5회 연결 시도
        for i in range(5):
            try:
                self.ws = websocket.create_connection("ws://172.30.1.17:8080/doorlock-ws")
                break
            except ConnectionRefusedError as e:
                self.connectionCount += 1
                print("연결 실패, 재시도 횟수 : %i" % self.connectionCount)
                time.sleep(2)

        if self.connectionCount >= 5:
            print("서버에 연결할 수 없습니다...")
            sys.exit(-1)

        self.recvThread = threading.Thread(target=self.recv)
        self.recvThread.start()
        self.service()

    def recv(self):
        while True:
            try:
                msg = self.ws.recv()
                body = json.loads(msg)

                # 기능별 수행
                if body['type'] == 100:  # doorlock open
                    data = json.loads(body['data'])

                    self.dno = data['dno']
                    self.location = data['location']
                    self.level = data['level']

                    print("서버에 연결되었습니다.")
                    print("============ 도어락 정보===========")
                    print("location :", self.location, ",", "level :", self.level)


                # info response : 사원 번호, 사원 이름, level, status
                elif body['type'] == 300:
                    employee = json.loads(body['data'])

                    result = self.certification(employee['level'], employee['eno'])

                    if result == True:
                        print("인증 성공")
                        openThread = threading.Thread(target=self.door_open)
                        openThread.start()
                        self.access_result(employee['eno'], self.dno, "success")
                    else:
                        print("인증 실패")
                        self.access_result(employee['eno'], self.dno, "fail")

                # remoteOpen : 원격 개방
                elif body['type'] == 400:
                    # 도어락 여는 코드
                    openThread = threading.Thread(target=self.door_open)
                    openThread.start()
                    print("원격 개방 실행")

                # Error 메시지
                elif body['type'] == 700:
                    data = body['data']
                    print(data)

            except Exception:
                self.shutdown()




    def service(self):
        self.init()

        while True:
            try:
                # 카드키 접촉 대기(test를 위해 사용자 입력 받음)
                time.sleep(3)
                eno = input("사원 번호 입력 : ")
                self.cert_request(int(eno))

            except Exception:
                print(Exception.args[0])
                self.shutdown()

    def sendLog(self, ** kwargs):
        message = json.dumps(kwargs)
        self.ws.send(message.encode('utf-8'))

    # 초기화
    def init(self):
        type = 100
        msg = {'type':type, 'data':self.mac}
        #print(json.dumps(msg))
        self.ws.send(json.dumps(msg))

    # 인증 요청
    def cert_request(self, eno):
        msg_dict = {'type':300, 'data': eno}
        msg_json = json.dumps(msg_dict)
        self.ws.send(msg_json)

    # 인증
    def certification(self, level, eno):
        if self.card(level) != 1:
            return False

        if self.level >= 2:
            if self.face(eno) != 1:
                return False

        return True

    # card 인증
    def card(self, level):
        #print('card 인증')
        if self.level <= level:
            return 1
        else:
            return 0

    # 얼굴 인증
    def face(self, eno):
        output = np.empty((240, 320, 3), dtype=np.uint8)

        print("Loading eno:1 face image")
        img_name = str(eno) + ".jpg"

        img_file = Path(img_name)
        if img_file.is_file():
            emp_image = face_recognition.load_image_file(img_name)
            emp_face_location = face_recognition.face_locations(emp_image)
            emp_face_encoding = face_recognition.face_encodings(emp_image, emp_face_location)[0]

            # init variables
            face_locations = []
            face_encodings = []

            try:
                self.camera = picamera.PiCamera()
                self.camera.resolution = (320, 240)
            
                print("Capturing image")
                self.camera.capture(output, format="rgb")

                self.camera.close()
                self.camera = None
            except:
                print("현재 카메라 사용이 불가능합니다")
                return 0

            face_locations = face_recognition.face_locations(output)
            print("Found {} faces in image.".format(len(face_locations)))
            face_encodings = face_recognition.face_encodings(output, face_locations)

            # Loop over each face found in the frame to see if it's someone we know.
            for face_encoding in face_encodings:
                # See if the face is a match for the known face(s)
                match = face_recognition.compare_faces([emp_face_encoding], face_encoding)
                distance = face_recognition.face_distance([emp_face_encoding], face_encoding)

                print("distance :", distance[0])

                if match[0]:
                    return 1

        else:
            print("해당 사원의 사진이 존재하지 않습니다..")
        return 0

    # Doorlock Open
    def door_open(self):
        if self.evt.isSet() == False:
            self.evt.set()
            GPIO.setmode(GPIO.BCM)
            GPIO.setup(4, GPIO.OUT)
            GPIO.output(4, True)
            time.sleep(0.5)
            GPIO.output(4, False)
            GPIO.cleanup()
            self.evt.clear()
        else:
            sys.exit()

    # 출입시도 결과 보내기
    def access_result(self, eno, dno, result):
        log = {'eno':eno, 'dno':dno, 'result':result}
        log_json = json.dumps(log)
        message = {'type':350, 'data':log_json}
        self.ws.send(json.dumps(message))


# Mac Address 받아오기
def get_mac():
    mac_num = hex(uuid.getnode()).replace('0x', '').upper()
    mac = ':'.join(mac_num[i : i + 2] for i in range(0, 11, 2))
    return mac


if __name__ == "__main__":
    try:
        clnt = Client()
    finally:
        GPIO.cleanup()
        pass
