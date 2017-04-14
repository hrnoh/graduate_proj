# RotatingFileHandler
import logging
import time

from logging.handlers import RotatingFileHandler


# ----------------------------------------------------------------------
def create_rotating_log(path):
    """
    Creates a rotating log
    """
    logger = logging.getLogger("Rotating Log")
    logger.setLevel(logging.INFO)

    # add a rotating handler
    # maxBytes에 도달 하면 .숫자를 붙인 로그파일 생성, backupCount는 로그파일을 만들 횟수
    handler = RotatingFileHandler(path, maxBytes=20, backupCount=5)
    logger.addHandler(handler)

    for i in range(10):
        logger.info("This is test log line %s" % i)
        time.sleep(1.5)


# ----------------------------------------------------------------------
if __name__ == "__main__":
    log_file = "test.log"
    create_rotating_log(log_file)