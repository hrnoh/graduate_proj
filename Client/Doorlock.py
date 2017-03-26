from socket import *
import sys
import RPi.GPIO as GPIO
import time

class Doorlock(object):
    def __init__(self):
        # GPIO 초기화
        GPIO.setmode(GPIO.BCM)
        GPIO.setup(4, GPIO.OUT)

        # 설정파일 불러오기
        path = "/etc/doorlock.conf"
        file = open(path, "r")
        self.name = file.readline()
        file.close()

        # DB에서 내용 가져오기 (미구현)

        # 서버 연결
        self.connection()

    def getName(self):
        return self.name

    def getSock(self):
        return self.sock

    def getLevel(self):
        return self.level

    def connection(self):
        # 소켓 생성
        self.sock = socket(AF_INET, SOCK_STREAM)
        self.sock.connect('127.0.0.1', 8888)

        # 도어락 이름 전송
        self.sock.send(self.name.encode('utf-8'))

    # 도어락 개방
    def switch_on(self):
        print("Doorlock open!")
        GPIO.output(4, True)
        time.sleep(0.3)
        GPIO.output(4, False)