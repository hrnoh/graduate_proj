#-*- coding: utf-8 -*-
import websocket
import threading
import json
import time
import sys
import uuid
import datetime
import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)
GPIO.setup(4, GPIO.OUT)

# Rule 설정
rule = {1: {'card':1, 'face':0, 'finger':0},
        2: {'card':1, 'face':1, 'finger':0},
        3: {'card':1, 'face':1, 'finger':1}}

class Client:
    def __init__(self):
        self.mac = get_mac()
        self.exitFlag = 0
        #self.lock = threading.Event()

        # 5회 연결 시도
        for i in range(5):
            try:
                self.ws = websocket.create_connection("ws://172.30.1.10:8080/doorlock/doorlock-ws")
                break
            except ConnectionRefusedError as e:
                print(e.args)
                time.sleep(2)

        self.recvThread = threading.Thread(target=self.recv)
        self.recvThread.daemon = True
        self.recvThread.start()
        self.service()

    def recv(self):
        try:
            while not self.exitFlag:
                msg = self.ws.recv()
                body = json.loads(msg)

                # 기능별 수행
                if body['type'] == "dinit":     # doorlock open
                    data = json.loads(body['data'])

                    self.dno = data['dno']
                    self.location = data['location']
                    self.level = data['level']

                    print("서버에 연결되었습니다.")
                    print("============ 도어락 정보===========")
                    print("location :", self.location, ",", "level :", self.level)


                # info response : 사원 번호, 사원 이름, level, status
                elif body['type'] == "info_response":
                    employee = json.loads(body['data'])
                    #print(employee)

                    result = self.certification(employee['level'])

                    if result == True:
                        print("인증 성공")
                    else:
                        print("인증 실패")

                # remoteOpen : 원격 개방
                elif body['type'] == 'remoteOpen':
                    # 도어락 여는 코드
                    door_open()

                # Error 메시지
                elif body['type'] == 'error':
                    data = body['data']
                    print(data)
                    
        except Exception:
            shutdown()




    def service(self):
        self.init()

        try:
            while not self.exitFlag:
                # 카드키 접촉 대기(test를 위해 사용자 입력 받음)
                time.sleep(3)
                eno = input("사원 번호 입력 : ")
                self.cert_request(int(eno))
                
        except Exception:
            shutdown()

    def sendLog(self, ** kwargs):
        message = json.dumps(kwargs)
        self.ws.send(message.encode('utf-8'))

    # 초기화
    def init(self):
        type = 'dinit'
        msg = {'type':type, 'data':self.mac}
        #print(json.dumps(msg))
        self.ws.send(json.dumps(msg))

    # 인증 요청
    def cert_request(self, eno):
        msg_dict = {'type':'info_request', 'data': eno}
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

        if proc['finger']:
            result_set['finger'] = self.finger(level)

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

    # 지문 인증
    def finger(self, level):
        #print('finger 인증')
        return 1

    # Shutdown
    def shutdown(self):
        GPIO.cleanup()
        sys.exit()
        

# Mac Address 받아오기
def get_mac():
    mac_num = hex(uuid.getnode()).replace('0x', '').upper()
    mac = '-'.join(mac_num[i : i + 2] for i in range(0, 11, 2))
    return mac

# Doorlock Open
def door_open():
    for i in range(2):
        GPIO.output(4, True)
        time.sleep(0.5)
        GPIO.output(4, False)
        time.sleep(10)


if __name__ == "__main__":
    clnt = Client()
