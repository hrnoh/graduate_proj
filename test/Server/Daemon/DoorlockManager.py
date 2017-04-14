from socket import *

# 도어락 클래스
from Doorlock import *

class DoorlockManager(object):
    def __init__(self, logger):
        self.doorlocks = []
        self.count = 0
        self.logger = logger

    # 도어락 추가
    def doorlock_add(self, name, sock, addr):
        doorlock = Doorlock(name, sock, addr)
        self.doorlocks.append(doorlock)
        self.count += 1

    # 도어락 제거
    def doorlock_del(self, sock):
        for doorlock in self.doorlocks:
            if doorlock.getSock() == sock:
                self.doorlocks.remove(doorlock)
                self.count -= 1

    # 도어락 메인 서비스
    def service(self, name, sock, addr):
        self.doorlock_add(name, sock, addr) # 도어락 추가
        self.logger.info("Doorlock-%s (%s)가 연결되었습니다." % (name, addr))
        self.logger.info("현재 연결 된 도어락 : %d대" % self.count)

        # 메인 service 로직
        while True:
            rawData = sock.recv(1024)
            if rawData == b"exit":
                break

            self.logger.info("receive: %s" % str(rawData))

            if rawData == b"test":
                self.logger.info("Doorlock-%s 를 개방합니다." % name)
                sock.send("open".encode("utf-8"))

        self.doorlock_del(sock) # 도어락 제거
        sock.close()    # 소켓 닫기
        self.logger.info("Doorlock-%s (%s)가 연결 해제되었습니다." % (name, addr))
        self.logger.info("현재 연결 된 도어락 : %d대" % self.count)
