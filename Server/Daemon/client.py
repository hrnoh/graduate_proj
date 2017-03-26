from socket import *
import sys

if len(sys.argv) != 2:
	print("Usage : %s <name>" % sys.argv[0])
	sys.exit(0)

# 네트워크 TCP 연결 초기화
print("Client TCP initialize...")

# 연결할 서버 주소 지정
serverName = "127.0.0.1"
serverPort = 8888

# 클라이언트 소켓 생성
clientSocket = socket(AF_INET, SOCK_STREAM)

# 서버에 연결 요청
clientSocket.connect((serverName, serverPort))
print("Server connected...")

# 도어락 이름
name = sys.argv[1]
clientSocket.send(name.encode("utf-8"))

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
