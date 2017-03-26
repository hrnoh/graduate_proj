# 분석하기!!
#!/opt/python3/bin/python3

import sys, os, time, atexit
from signal import SIGTERM
import logging
import logging.handlers
from socket import *
import threading
import traceback
from DoorlockManager import *

# 로거 설정
logger = logging.getLogger()
logger.setLevel(logging.DEBUG)
filehandler = logging.handlers.TimedRotatingFileHandler('/tmp/daemon.log',when='midnight',interval=1,backupCount=10)
filehandler.setFormatter(logging.Formatter(fmt='%(asctime)s %(levelname)s %(message)s', datefmt='%Y-%m-%d %H:%M:%S'))
logger.addHandler(filehandler)

# 데몬 클래스
class Daemon(object):
    """
    Daemon 클래스 상속 후, run 메소드 오버라이드 하기
    """
    def __init__(self, pidfile, stdin='/dev/null', stdout='/dev/null', stderr='/dev/null'):
        self.stdin = stdin
        self.stdout = stdout
        self.stderr = stderr
        self.pidfile = pidfile

    def daemonize(self):
        """
        Make daemon process
        """
        try:
            pid = os.fork()
            if pid > 0:
                  # 첫번째 부모 종료.
                sys.exit(0)
        except OSError as e:
            message = "Fork #1 failed: {}\n".format(e)
            sys.stderr.write(message)
            sys.exit(1)

         # 부모 프로세스 환경 분리
        os.chdir("/")
        os.setsid()
        os.umask(0)

         # 두번째 fork.
        try:
            pid = os.fork()
            if pid > 0:
                  # 두번째 부모 종료.
                sys.exit(0)
        except OSError as e:
            message = "Fork #2 failed: {}\n".format(e)
            sys.stderr.write(message)
            sys.exit(1)

        logger.info('deamon going to background, PID: {}'.format(os.getpid()))

         # 표준 파일 디스크립터 재지정
        """
        sys.stdout.flush()
        sys.stderr.flush()
        si = open(self.stdin, 'r')
        so = open(self.stdout, 'a+')
        se = open(self.stderr, 'a+')
        os.dup2(si.fileno(), sys.stdin.fileno())
        os.dup2(so.fileno(), sys.stdout.fileno())
        os.dup2(se.fileno(), sys.stderr.fileno())
        """

        # pid파일 쓰기
        pid = str(os.getpid())
        open(self.pidfile,'w+').write("{}\n".format(pid))

        # daemon 종료 시 호출되는 콜백 함수 지정
        atexit.register(self.delpid)

    def delpid(self):
        logger.info("Server closed....")
        os.remove(self.pidfile)

    def start(self):
        """
        Start daemon.
        """
        # Check pidfile to see if the daemon already runs.
        try:
            pf = open(self.pidfile,'r')
            pid = int(pf.read().strip())
            pf.close()
        except IOError:
            pid = None

        if pid:
            message = "Pidfile {} already exist. Daemon already running?\n".format(self.pidfile)
            sys.stderr.write(message)
            sys.exit(1)

        # Start daemon.
        self.daemonize()
        self.run()

    def status(self):
        """
        Get status of daemon.
        """
        try:
            pf = open(self.pidfile,'r')
            pid = int(pf.read().strip())
            pf.close()
        except IOError:
            message = "There is not PID file. Daemon already running?\n"
            sys.stderr.write(message)
            sys.exit(1)

        try:
            procfile = open("/proc/{}/status".format(pid), 'r')
            procfile.close()
            message = "There is a process with the PID {}\n".format(pid)
            sys.stdout.write(message)
        except IOError:
            message = "There is not a process with the PID {}\n".format(self.pidfile)
            sys.stdout.write(message)

    def stop(self):
        """
        Stop the daemon.
        """
        # Get the pid from pidfile.
        try:
            pf = open(self.pidfile,'r')
            pid = int(pf.read().strip())
            pf.close()
        except IOError as e:
            message = str(e) + "\nDaemon not running?\n"
            sys.stderr.write(message)
            sys.exit(1)

        # Try killing daemon process.
        try:
            os.kill(pid, SIGTERM)
            time.sleep(1)
        except OSError as e:
            print(str(e))
            sys.exit(1)

        try:
            if os.path.exists(self.pidfile):
               os.remove(self.pidfile)
        except IOError as e:
            message = str(e) + "\nCan not remove pid file {}".format(self.pidfile)
            sys.stderr.write(message)
            sys.exit(1)

    def restart(self):
        """
        Restart daemon.
        """
        self.stop()
        time.sleep(1)
        self.start()

    def run(self):
        """
        서버 데몬 실행을 위해 run메서드를 재정의한다.

        Example:

        class MyDaemon(Daemon):
            def run(self):
        """

class MyDaemon(Daemon):
    def run(self):
        logger.info("Welcome to IoT Doorlock Server...")
        logger.info("Server is running...")

        clntCount = 0
        clntList = []

        # 도어락 관리 시스템 생성
        manager = DoorlockManager(logger)

        # 서버 소켓 생성
        serverSocket = socket(AF_INET, SOCK_STREAM)

        # 주소 할당
        serverSocket.bind(('', 8888))

        # 연결 요청 큐 생성
        serverSocket.listen(5)

        while True:
                # 연결 요청 수락
                clntSock, addr = serverSocket.accept()
                clntCount += 1
                try:
                        rawData = clntSock.recv(1024)
                        name = str(rawData)
                        clnt = threading.Thread(target=manager.service, args=(name, clntSock, addr))
                        clnt.start()
                except:
                        logger.warning("Thread Error!")
                        traceback.print_exc()

                clntList.append(clnt)

        for clnt in clntList:
                clnt.join()

if __name__ == "__main__":
    daemon = MyDaemon('/tmp/python-daemon.pid')
    if len(sys.argv) == 2:
        logger.info('{} {}'.format(sys.argv[0],sys.argv[1]))

        if 'start' == sys.argv[1]:
            daemon.start()
        elif 'stop' == sys.argv[1]:
            daemon.stop()
        elif 'restart' == sys.argv[1]:
            daemon.restart()
        elif 'status' == sys.argv[1]:
            daemon.status()
        else:
            print ("Unknown command")
            sys.exit(2)

        sys.exit(0)
    else:
        logger.warning('show cmd deamon usage')
        print ("Usage: {} start|stop|restart".format(sys.argv[0]))
        sys.exit(2)
