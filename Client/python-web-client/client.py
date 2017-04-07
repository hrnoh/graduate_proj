#-*- coding: utf-8 -*-
import websocket
import threading
import json
import time
import datetime

class Client:
    def __init__(self):
        self.ws = websocket.create_connection("ws://localhost:8080/doorlock/doorlock-ws")
        self.location = 'edit-room'
        self.recvThread = threading.Thread(target=self.recv)
        self.recvThread.daemon = True
        self.recvThread.start()
        self.service()

    def recv(self):
        while True:
            data = self.ws.recv()

            # 기능별 수행
            if data == "open":     # doorlock open
                print("doorlock opened")
                now = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                self.sendLog(location = self.location, who='Hyeong Rae', date=now)

    def service(self):
        time.sleep(5)
        self.ws.send("test")
        time.sleep(5)

    def sendLog(self, ** kwargs):
        message = json.dumps(kwargs)
        self.ws.send(message.encode('utf-8'))


if __name__ == "__main__":
    clnt = Client()