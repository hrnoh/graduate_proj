"""
 queue 모듈은 item들의 특정 번호를 저장할 수 있는 큐 객체를 만들 수 있게 도와준다.
 큐의 메소드는 다음과 같다.

 get() : 큐에 있는 item을 제거하고 반환한다.
 put() : 큐에 item을 추가한다.
 qsize() : 큐의 현재 item 수를 반환한다.
 empty() : 큐가 비어있으면 True, 아니면 False를 반환한다.
 full() : 큐가 꽉 찼으면 True, 아니면 False를 반환한다.
"""
import queue
import threading
import time

exitFlag = 0

class myThread(threading.Thread):
    def __init__(self, threadID, name, q):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.q = q

    def run(self):
        print("Starting ", self.name)
        process_data(self.name, self.q)
        print("Exiting ", self.name)

def process_data(threadName, q):
    while not exitFlag:
        queueLock.acquire()
        if not workQueue.empty():
            data = q.get()
            queueLock.release()
            print("%s processing %s" % (threadName, data))
        else:
            queueLock.release()

        time.sleep(1)

threadList = ["Thread-1", "Thread-2", "Thread-3"]
nameList = ["One", "Two", "Three", "Four", "Five"]
queueLock = threading.Lock()
workQueue = queue.Queue(10) # size 10인 큐 생성
threads = []
threadID = 1

# 새로운 쓰레드 생성
for tName in threadList:
    thread = myThread(threadID, tName, workQueue)
    thread.start()
    threads.append(thread)
    threadID += 1

# 큐 채우기
queueLock.acquire()
for word in nameList:
    workQueue.put(word)
queueLock.release()

# 큐가 빌때까지 대기
while not workQueue.empty():
    pass

# 쓰레드에게 종료하라는 알림
exitFlag = 1

# 모든 쓰레드의 종료 대기
for t in threads:
    t.join()

print("Exiting Main Thread")