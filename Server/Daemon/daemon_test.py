import os
import sys
import time

class Daemon:
    """
    서버 데몬
    """

    def __init__(self):
        self.doInit()

    def doInit(self):
        try:
            pid = os.fork()

            # 부모 프로세스 종료
            if pid > 0:
                print("PID: %d" % pid)
                sys.exit()

        except OSError as error:
            print("Unable to form. Error: %d (%s)" % (error.errno, error.strerror))
            sys.exit()

        self.doTask()

    def doTask(self):
        """새로운 세션 생성"""
        os.setsid()

        os.open("/dev/null", os.O_RDWR)
        os.dup(0)
        os.dup(0)

        # 데몬이 할 일
        for i in range(5):
            print("Hello my name is Daemon %d" % i)
            time.sleep(1)

if __name__ == "__main__":
    daemon = Daemon()