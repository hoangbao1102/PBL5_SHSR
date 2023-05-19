import sqlite3
import RPi.GPIO as GPIO

# Kết nối đến cơ sở dữ liệu SQLite
conn = sqlite3.connect('lights.db')

# Đặt chế độ GPIO
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(18, GPIO.OUT)

# Hàm để lấy trạng thái của đèn
def get_light_status():
    c = conn.cursor()
    c.execute('SELECT status FROM lights WHERE id = 1')
    result = c.fetchone()
    return result[0]

# Hàm để cập nhật trạng thái đèn
def set_light_status(status):
    c = conn.cursor()
    c.execute('UPDATE lights SET status = ? WHERE id = 1', (status,))
    conn.commit()
    GPIO.output(18, status)

# Kiểm tra trạng thái đèn
if get_light_status() == 1:
    # Nếu đèn đang bật, tắt đèn
    set_light_status(0)
else:
    # Nếu đèn đang tắt, bật đèn
    set_light_status(1)

# Ngắt kết nối đến cơ sở dữ liệu SQLite
conn.close()
