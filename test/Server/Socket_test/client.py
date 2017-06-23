from socket import *

# 네트워크 TCP 연결 초기화
print("Client TCP initialize...")

# 연결할 서버 주소 지정
serverName = "127.0.0.1"
serverPort = 59999

# 클라이언트 소켓 생성
clientSocket = socket(AF_INET, SOCK_STREAM)

# 서버에 연결 요청
clientSocket.connect((serverName, serverPort))
print("Server connected...")

while True:
	rawData = input("Input string : ")

	# 문자열 인코드하여 데이터 송신
	clientSocket.send(rawData.encode("utf-8"))

	# exit 치면 종료
	if rawData == "exit":
		break

	# 데이터 수신
	modifiedStr = clientSocket.recv(1024)
	print("from server: ", str(modifiedStr))
