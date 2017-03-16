from socket import *

# 네트워크 TCP 초기화
print("Server TCP initialize...")

# 호스트 주소 지정
serverPort = 9190

# 서버 소켓 생성
serverSocket = socket(AF_INET, SOCK_STREAM)

# 주소와 소켓 결합
serverSocket.bind(('', serverPort))

# 연결 요청 대기
serverSocket.listen(1)

while True:
    #연결 요청 수학, 연결 소켓 리턴
    connectionSocket, addr = serverSocket.accept()
    print("Client connected...", addr)

    #데이터 수신
    rawData = connectionSocket.recv(1024)
    print("receive: ", rawData)

    #대문자 변환
    upperStr = rawData.upper()

    #데이터 송신
    connectionSocket.send(upperStr)
    connectionSocket.close()