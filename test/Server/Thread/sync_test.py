import threading
import time

class myThread(threading.Thread):
    def __init__(self, threadID, name, counter):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.counter = counter

    def run(self):
        print("Starting ", self.name)
        # 쓰레드 동기화를 위한 lock 얻기
        threadLock.acquire()
        print_time(self.name, self.counter, 3)
        # lock 해제
        threadLock.release()

def print_time(threadName, delay, counter):
    while counter:
        time.sleep(delay)
        print("%s: %s" % (threadName, time.ctime(time.time())))
        counter -= 1

threadLock = threading.Lock()
threads = []

# 새로운 쓰레드 생성
thread1 = myThread(1, "Thread-1", 1)
thread2 = myThread(2, "Thread-2", 2)

# 쓰레드 시작
thread1.start()
thread2.start()

# 쓰레드 리스트에 쓰레드 추가
threads.append(thread1)
threads.append(thread2)

# 쓰레드 종료 기다리기
for t in threads:
    t.join()

print("Exiting Main Thread")