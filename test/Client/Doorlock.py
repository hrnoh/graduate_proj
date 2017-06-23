from socket import *
import sys
import RPi.GPIO as GPIO
import time
import DBConn

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

        # DB에서 내용 가져오기
        self.db = DBConn
        conn = self.db.getConnection()

        # 커서 생성
        curs = self.conn.cursor()

        # 도어락 정보 가져오기
        sql = "select * from doorlock where name = %s" % self.name
        curs.execute(sql)

        # 데이터 fetch
        rows = curs.fetchall()
        if len(rows) != 1:
            print("Doorlock Initialize error!")
            self.db.close()
            sys.exit(-1)

        # 상태정보, 레벨 저장
        self.status = rows[0][1]
        self.level = rows[0][3]

        # 서버 연결
        self.connection()

    def getName(self):
        return self.name

    def getStatus(self):
        return self.status

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

    # 프로그램 종료
    def prog_exit(self):
        # 소켓 닫기
        if self.sock != None:
            self.sock.send("exit".encode("utf-8"))
            self.sock.close()

        # DB 연결 종료
        self.db.close()
        sys.exit(0)

    # 도어락 개방
    def switch_on(self):
        print("Doorlock open!")
        GPIO.output(4, True)
        time.sleep(0.3)
        GPIO.output(4, False)