import firebase_admin

from firebase_admin import credentials

from firebase_admin import db

from firebase_admin import storage

import os

import RPi.GPIO as GPIO

import time

import threading

#ketnoi firebase 

def ketnoi():

    cred = credentials.Certificate("pbl5shsr-firebase-adminsdk-u4jih-5de6689dfa.json")

    firebase_admin.initialize_app(cred, {

        'databaseURL': 'https://pbl5shsr-default-rtdb.asia-southeast1.firebasedatabase.app',

        'storageBucket': 'gs://pbl5shsr.appspot.com'

    })

def batden(status):

     GPIO.setmode(GPIO.BCM)

     GPIO.setwarnings(False)

     GPIO.setup(22, GPIO.OUT)

     GPIO.output(22, status)

     

     print(status)

def mocua(status):

     GPIO.setmode(GPIO.BCM)

     servo_pin = 17

     GPIO.setup(servo_pin, GPIO.OUT)



     # Khai báo PWM với tần số 50 Hz

     pwm = GPIO.PWM(servo_pin, 50)

     pwm.start(0)

     if status == 0:

         pwm.ChangeDutyCycle(2.5)  # 0 độ

         time.sleep(1)

        

     elif status == 1:

         pwm.ChangeDutyCycle(7.5)  # 90 độ

         time.sleep(1)

     else:

         print("Status không hợp lệ!")



     # Dừng PWM và giải phóng GPIO

     pwm.stop()

     





     print(status)

def batdieuhoa(status):

    GPIO.setmode(GPIO.BCM)

    GPIO.setwarnings(False)

    GPIO.setup(26, GPIO.OUT)

    GPIO.output(26, status)

    print(status)

def batquat():

    GPIO.setmode(GPIO.BCM)

    GPIO.setwarnings(False)

    Ena, In1, In2 = 2, 3, 4

    GPIO.setup(Ena, GPIO.OUT)

    GPIO.setup(In1, GPIO.OUT)

    GPIO.setup(In2, GPIO.OUT)

    pwm = GPIO.PWM(Ena, 100)

    pwm.start(0)

    

    while kiemtraquat()==1:

        if (kiemtratocdoquat()==1):

            t=50

        else:

            t=25

        

        GPIO.output(In1, GPIO.HIGH)

        GPIO.output(In2, GPIO.LOW)

        pwm.ChangeDutyCycle(t)

        print(t)   

        print("batquat")   

        

        

        

    pwm.stop()

    

    print(kiemtraquat())

def kiemtratocdoquat():

    devices_ref = db.reference('devices')

    trangthai_ref = devices_ref.child('device4')

    trangthai_val = trangthai_ref.child('speed').get()

    return trangthai_val

def kiemtraquat():

    devices_ref = db.reference('devices')

    trangthai_ref = devices_ref.child('device4')

    trangthai_val = trangthai_ref.child('status').get()

    return trangthai_val

def on_data_change_other(event):

    device_name=event.path.split('/')[-2]

    print(device_name)

    print('Data changed:', event.data)

    if(device_name=="device1"):

        batden(event.data)

    if(device_name=="device2"):

        mocua(event.data)  

    if(device_name=="device3"):

        batdieuhoa(event.data)  

    #if(device_name=="device4"):

        #batquat(event.data) 

def on_data_change_quat(event):

    device_name=event.path.split('/')[-2]

    if(device_name=="device4"):

        batquat()

def langnghedb_other():

    devices_ref = db.reference('devices')

    devices_data = devices_ref.get()

    devices_ref.listen(on_data_change_other)

def langnghedb_quat():

    devices_ref = db.reference('devices')

    devices_data = devices_ref.get()

    devices_ref.listen(on_data_change_quat)

def langnghedb():

    # Lấy reference đến node "devices"

    devices_ref = db.reference('devices')



    # Lấy dữ liệu từ node "devices"

    devices_data = devices_ref.get()

    devices_ref.listen(on_data_change)



    # Lặp vô hạn để tiếp tục lắng nghe sự thay đổi của Firebase Realtime Database

    



    

if __name__ == '__main__':

    ketnoi()

    #langnghedb()

    threading.Thread(target=langnghedb_other).start()

    threading.Thread(target=langnghedb_quat).start()

    

