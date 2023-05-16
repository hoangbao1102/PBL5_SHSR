import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from firebase_admin import storage
import os
import threading
# import RPi.GPIO as GPIO
#ketnoi firebase 
def ketnoi():
    cred = credentials.Certificate("pbl5shsr-firebase-adminsdk-u4jih-5de6689dfa.json")
    firebase_admin.initialize_app(cred, {
        'databaseURL': 'https://pbl5shsr-default-rtdb.asia-southeast1.firebasedatabase.app',
        'storageBucket': 'gs://pbl5shsr.appspot.com'
    })
def batden(status):
#     GPIO.setmode(GPIO.BCM)
#     GPIO.setwarnings(False)
#     GPIO.setup(18, GPIO.OUT)
#     GPIO.output(18, status)
      print(status)
def mocua(status):
    # servo_pin = 11
    # GPIO.setup(servo_pin, GPIO.OUT)

    # # Khai báo PWM với tần số 50 Hz
    # pwm = GPIO.PWM(servo_pin, 50)
    # pwm.start(0)
    # if status == 0:
    #     pwm.ChangeDutyCycle(2.5)  # 0 độ
    #     time.sleep(1)
    # elif status == 1:
    #     pwm.ChangeDutyCycle(7.5)  # 90 độ
    #     time.sleep(1)
    # else:
    #     print("Status không hợp lệ!")

    # # Dừng PWM và giải phóng GPIO
    # pwm.stop()
    # GPIO.cleanup()


    print(status)
def batdieuhoa(status):
#     GPIO.setmode(GPIO.BCM)
#     GPIO.setwarnings(False)
#     GPIO.setup(16, GPIO.OUT)
#     GPIO.output(16, status)
      print(status)
def kiemtraquat():
    devices_ref = db.reference('devices')
    trangthai_ref = devices_ref.child('device4')
    trangthai_val = trangthai_ref.child('status').get()
    return trangthai_val
def batquat(status):
    while status==1:
        if(kiemtraquat()==1):
            print("bat quat")
        else:
            break
    print(status)
def on_data_change(event):
    device_name = os.path.basename(os.path.dirname(event.path))
    print(device_name)
    print('Data changed:', event.data)
    if(device_name=="device1"):
        batden(event.data)
    if(device_name=="device2"):
        mocua(event.data)  
    if(device_name=="device3"):
        batdieuhoa(event.data)  
    if(device_name=="device4"):
        batquat(event.data) 
def langnghedb():
    # Lấy reference đến node "devices"
    devices_ref = db.reference('devices')

    # Lấy dữ liệu từ node "devices"
    devices_data = devices_ref.get()
    devices_ref.listen(on_data_change)

    # Lặp vô hạn để tiếp tục lắng nghe sự thay đổi của Firebase Realtime Database
    

if __name__ == '__main__':
    ketnoi()
    threading.Thread(target=batquat).start()
    langnghedb()
    
    