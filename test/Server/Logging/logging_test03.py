import logging
logging.basicConfig(filename='test.log', level=logging.DEBUG)

logging.info("====================================")
logging.info("파일에 로그 남기기...")
logging.info("====================================")
logging.debug("디버깅용 로그")
logging.warning("주의!")
logging.error("에러 발생!!")
logging.critical("심각한 에러!!")