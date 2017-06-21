#-*- coding: utf-8 -*-
import websocket
import threading
import json
import time
import sys
import uuid
import datetime
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

                    result = self.certification(employee['level'])

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
    def certification(self, level):
        proc = rule[level]
        result_set = proc

        if proc['card']:
            result_set['card'] = self.card(level)

        if proc['face']:
            result_set['face'] = self.face(level)

        proc_list = list(proc.values())
        result_set_list = list(result_set.values())

        for i in range(3):
            if proc_list[i] != result_set_list[i]:
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
    def face(self, level):
        #print('face 인증')
        return 1

    # Doorlock Open
    def door_open(self):
        if self.evt.isSet() == False:
            self.evt.set()
            GPIO.output(4, True)
            time.sleep(0.5)
            GPIO.output(4, False)
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
        # GPIO 활성화
        GPIO.setmode(GPIO.BCM)
        GPIO.setup(4, GPIO.OUT)

        # Rule 설정
        rule = {1: {'card': 1, 'face': 0},
                2: {'card': 1, 'face': 1},
                3: {'card': 1, 'face': 1}}

        clnt = Client()
    finally:
        GPIO.cleanup()
        pass
