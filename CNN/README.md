# PBL5- Automatic-Speech-Recognition

Project này sử dụng thư viện keras để xây dựng mô hình nhận dạng giọng nói dựa trên CNN

## Bắt đầu

Những hướng dẫn này sẽ giúp bạn có thể chạy project này trên máy của bạn cho mục đích phát triển và thử nghiệm.  
1) Đảm bảo cài đặt python 3 trên thiết bị của bạn trước khi chuyển sang các bước tiếp theo.
2) Hãy cài đặt các thư viện cần thiết trong requirements.txt

```angular2html
pip install -r requirements.txt
```

## Cài đặt

Chuỗi ví dụ từng bước cho bạn biết cách chạy project
```
1) Tải project về máy của bạn
2) Đảm bảo rằng bạn đã cài đặt các thư viện cần thiết trong requirements.txt
3) Hãy thu âm các lệnh dữ liệu từ nhiều người,ở đây tôi thu âm 10 người với 10 câu lệnh khác nhau,mỗi câu lệnh được thực hiện nói 10 lần. Bạn có thể thay đổi theo nhu cầu của mình
4) Bạn sẽ cần phân chia các câu lệnh vào từng thư mục mang label của chúng.
5) Sau khi có dữ liệu,bạn hãy xem qua file `Script.ipynb` và `script_2.ipynb` để có thể phân chia dữ liệu train test val giống như của tôi. 2 file này tôi không để comment nhưng nó khá dễ hiểu để nhìn ra được công việc của chúng là gì.
6) Chạy `Using_tf.ipynb` để xem quá trình xử lí dữ liệu và huấn luyện mô hình
```
## Chạy thử nghiệm  

Chạy `main.ipynb` để thử nghiệm thực tế  

Thời gian ghi âm để nhận dạng là 2s nên bạn nói sau khi nhận được thông báo  
Sau khi bạn chạy cell `Test` trong `main.ipynb` sẽ xuất ra thông báo `Bắt đầu ghi âm`, đọc lệnh bạn muốn nhận dạng và chở xem kết quả  

## Xây dựng dựa trên

* [Keras](https://www.Keras.com) - Thư viện học máy
* [TensorFlow](https://www.Tensorflow.com) - Thư viện học máy

## Tài liệu tham khảo
* [Audio Classification Using CNN](https://medium.com/x8-the-ai-community/audio-classification-using-cnn-coding-example-f9cbd272269e)
* [Audio Deep Learning Made Simple](https://towardsdatascience.com/audio-deep-learning-made-simple-sound-classification-step-by-step-cebc936bbe5)
* [Sound-Classification-Mel-Spectrogram](https://github.com/OmarMedhat22/Sound-Classification-Mel-Spectrogram)