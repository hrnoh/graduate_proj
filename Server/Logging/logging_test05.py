import logging
import logging.handlers

# 로거 인스턴스 생성
logger = logging.getLogger('myloger')

# 포매터 만들기
formatter = logging.Formatter('[%(levelname)s|%(filename)s:%(lineno)s] %(asctime)s > %(message)s')

# 스트림과 파일로 로그를 출력하는 핸들러 만들기
fileHandler = logging.FileHandler('myLoggerTest.log')
streamHandler = logging.StreamHandler()

# 각 핸들러에 포매터 지정
fileHandler.setFormatter(formatter)
streamHandler.setFormatter(formatter)

# 로거 인스턴스에 핸들러 붙이기
logger.addHandler(fileHandler)
logger.addHandler(streamHandler)

# 로그 찍기
logger.setLevel(logging.DEBUG)
logger.debug("===========================")
logger.info("TEST START")
logger.warning("스트림으로 로그가 남아요~")
logger.error("파일로도 남으니 안심이죠~!")
logger.critical("치명적인 버그는 꼭 파일로 남기기도 하고 메일로 발송하세요!")
logger.debug("===========================")
logger.info("TEST END!")