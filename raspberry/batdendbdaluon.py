import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from firebase_admin import storage
import os
import threading
import modelcnn
import requests
from hmm_predict import HMMRecognition
# import hmm_predict
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
def on_data_change_other(event):
    device_name = os.path.basename(os.path.dirname(event.path))
    print(device_name)
    print('Data changed:', event.data)
    if(device_name=="device1"):
        batden(event.data)
    if(device_name=="device2"):
        mocua(event.data)  
    if(device_name=="device3"):
        batdieuhoa(event.data)  
    # if(device_name=="device4"):
    #     batquat(event.data) 
def on_data_change_quat(event):
    
    device_name = os.path.basename(os.path.dirname(event.path))
    if(device_name=="device4"):
        batquat(event.data)

def on_data_change_audio(event):
    print('Data changed:', event.data)
    audios_ref = db.reference('audio')
    file_ref = audios_ref.child('file_url')
    file_urllink=file_ref.get()
    print(file_urllink)
    response = requests.get(file_urllink)
    
    with open("new_audio.wav", "wb") as f:
     f.write(response.content)
    model_ref = db.reference('model')
    modelstring=model_ref.get()
    print(modelstring)
    if modelstring == 1:
        print("cnn")
        string=modelcnn.predict()
    else:
        print("hmm")
        string=hmm_reg.predict("new_audio.wav")
    if "quat" in string:
        refup = db.reference('devices/device4')
        if "tat" in string:
            refup.update({'status': 0})
        elif "bat" in string:
            refup.update({'status': 1})
        elif "tang toc do" in string:
            refup.update({'speed': 1})
        else: 
            refup.update({'speed': 0})
        
    elif "cua" in string:
        refup = db.reference('devices/device2')
        if "tat" in string:
            refup.update({'status': 0})
        elif "bat" in string:
            refup.update({'status': 1})
        else: 
            refup.update({'status': 0})
    elif "den" in string:
        refup = db.reference('devices/device1')
        if "tat" in string:
            refup.update({'status': 0})
        elif "bat" in string:
            refup.update({'status': 1})
        else: 
            refup.update({'status': 1})
    elif "dieuhoa" in string:
        refup = db.reference('devices/device3')
        if "tat" in string:
            refup.update({'status': 0})
        elif "bat" in string:
            refup.update({'status': 1})
        else: 
            refup.update({'status': 1})    
    else:
        print('not found')
    
def langnghedb_other():
    # Lấy reference đến node "devices"
    devices_ref = db.reference('devices')

    # Lấy dữ liệu từ node "devices"
    devices_data = devices_ref.get()
    devices_ref.listen(on_data_change_other)

    # Lặp vô hạn để tiếp tục lắng nghe sự thay đổi của Firebase Realtime Database
    
def langnghedb_quat():
    # Lấy reference đến node "devices"
    devices_ref = db.reference('devices')

    # Lấy dữ liệu từ node "devices"
    devices_data = devices_ref.get()
    devices_ref.listen(on_data_change_quat)
def langnghedb_audio():
    #lay link url 
    audios_ref = db.reference('audio')
    file_ref = audios_ref.child('file_url')
    file_urllink=file_ref.get()
    
    audios_ref.child('file_url').listen(on_data_change_audio)
if __name__ == '__main__':
    ketnoi()
    hmm_reg = HMMRecognition()
    
    
    threading.Thread(target=langnghedb_audio).start()
    threading.Thread(target=langnghedb_quat).start()
    threading.Thread(target=langnghedb_other).start()