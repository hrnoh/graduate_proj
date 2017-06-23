import pymysql

"""
    pip install pymysql로 Connection 모듈 install해야 함!
"""

class DBConn:
    def __init__(self):
        self.conn = 0
        self.serverIP = '127.0.0.1'
        self.user = 'graduate'
        self.password = 'passwd'
        self.charset = 'utf8'

    # DB 연결
    def getConnection(self):
        if self.conn == 0:
            self.conn = pymysql.connect(host=self.serverIP, user=self.user, password=self.password, charset=self.charset)

        return self.conn

    # DB 닫기
    def close(self):
        if self.conn != 0:
            self.conn.close()

        self.conn = 0