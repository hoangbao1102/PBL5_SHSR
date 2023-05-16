import sqlite3

# Kết nối đến cơ sở dữ liệu SQLite
conn = sqlite3.connect('lights.db')

# Tạo bảng để lưu trữ trạng thái của đèn
c = conn.cursor()
c.execute('''CREATE TABLE lights
             (id INTEGER PRIMARY KEY, status INTEGER)''')

# Thêm một bản ghi mặc định với trạng thái ban đầu là 0
c.execute('INSERT INTO lights (id, status) VALUES (1, 0)')
c.execute('INSERT INTO lights (id, status) VALUES (2, 1)')


# Lưu các thay đổi vào cơ sở dữ liệu
conn.commit()

# Ngắt kết nối đến cơ sở dữ liệu SQLite
conn.close()
