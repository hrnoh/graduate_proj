"""
    로거 사용하기!
    1. 로거 인스턴스 생성
    2. 스트림과 파일로 로그를 출력하는 핸들러를 각각만들기
    3. 1번에서 만든 로거 인스턴스에 스트림 핸들러와 파일 핸들러 붙이기
    4. 로거 인스턴스로 로그 찍기
"""
import logging
import logging.handlers

# 1. 로거 인스턴스 생성
logger = logging.getLogger('mylogger')

# 2. 스트림과 파일로 로그를 출력하는 핸들러 각각 만든다.
fileHandler = logging.FileHandler('myLoggerTest.log')
streamHandler = logging.StreamHandler()

# 3. 1번에서 만든 로거 인스턴스에 핸들러 붙이기
logger.addHandler(fileHandler)
logger.addHandler(streamHandler)

# 4. 로거 인스턴스로 로그 찍기
logger.setLevel(logging.DEBUG)
logger.debug("================================")
logger.info("TEST START")
logger.warning("스트림으로도 로그가 남는다.")
logger.error("파일로도 로그가 남는다.")
logger.critical("치명적 에러")
logger.debug("================================")
logger.info("TEST END!")