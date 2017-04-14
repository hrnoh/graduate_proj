import pymysql

# MySQL Connection 연결
conn = pymysql.connect(host='localhost', user='graduate', password='passwd', db='graduatedb', charset='utf8')

# Cursor 생성
curs = conn.cursor()

# SQL문 실행
sql = "select * from doorlock"
curs.execute(sql)

# 데이터 fetch
rows = curs.fetchall()
print(rows[0])  # 첫번째 행

# Connection 닫기
conn.close()