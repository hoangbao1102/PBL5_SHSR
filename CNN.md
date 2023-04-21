# Using Convolutional Neural Network (CNN)

Trong nhận dạng tiếng nói, mô hình CNN (Convolutional Neural Network) thường được sử dụng để trích xuất đặc trưng từ tín hiệu giọng nói. Sau đó, các đặc trưng này được đưa vào một mô hình phân loại để xác định loại tiếng nói.

Dưới đây là một số bước cơ bản để triển khai một mô hình CNN trong nhận dạng tiếng nói:

1. Thu thập và tiền xử lý dữ liệu: Thu thập các tập dữ liệu tiếng nói và tiền xử lý chúng để chuẩn bị cho quá trình huấn luyện.

2. Chuẩn bị dữ liệu huấn luyện và kiểm tra: Chia tập dữ liệu thành tập huấn luyện và tập kiểm tra. Tập huấn luyện được sử dụng để huấn luyện mô hình, trong khi tập kiểm tra được sử dụng để đánh giá mô hình sau khi huấn luyện.

3. Trích xuất đặc trưng: Sử dụng các phương pháp trích xuất đặc trưng như Mel-frequency cepstral coefficients (MFCC) để chuyển đổi tín hiệu giọng nói thành các đặc trưng số học có thể được sử dụng trong mô hình CNN.

4. Xây dựng mô hình CNN: Xây dựng một mô hình CNN với các lớp Conv2D và MaxPooling2D để trích xuất đặc trưng, sau đó là một hoặc nhiều lớp Dense để phân loại. Các tham số của mô hình, bao gồm số lượng lớp, số lượng bộ lọc và kích thước của chúng, số lượng nơ-ron trong mỗi lớp Dense, vv., được tinh chỉnh thông qua việc huấn luyện và đánh giá mô hình.

5. Huấn luyện mô hình: Sử dụng tập dữ liệu huấn luyện để huấn luyện mô hình CNN. Các tham số của mô hình được cập nhật để cải thiện độ chính xác của mô hình.

6. Đánh giá mô hình: Sử dụng tập dữ liệu kiểm tra để đánh giá độ chính xác của mô hình. Nếu mô hình có độ chính xác cao, nó có thể được sử dụng để dự đoán loại tiếng nói từ các tín hiệu giọng nói mới.

7. Triển khai mô hình: Mô hình CNN có thể được triển khai trong các ứng dụng thực tế,có thể triển khai mô hình CNN trong nhận dạng tiếng nói trên nhiều nền tảng khác nhau, bao gồm cả ứng dụng di động và web. Để triển khai mô hình CNN trên các nền tảng này, cần phải sử dụng các công cụ phát triển phần mềm và thư viện hỗ trợ như TensorFlow, PyTorch, Keras, OpenCV, vv.

Ví dụ, để triển khai mô hình CNN trên ứng dụng di động, có thể sử dụng các công cụ như TensorFlow Lite hoặc PyTorch Mobile. Các thư viện này cho phép triển khai mô hình trên các thiết bị di động như điện thoại thông minh và máy tính bảng một cách hiệu quả. Ngoài ra, cần phải quản lý kích thước của mô hình để đảm bảo rằng nó có thể chạy trên các thiết bị với tài nguyên có hạn.

Để triển khai mô hình CNN trên web, có thể sử dụng các công nghệ như JavaScript và WebAssembly để chạy mô hình trên trình duyệt web. Các thư viện như TensorFlow.js và ONNX.js cung cấp các công cụ hỗ trợ để triển khai mô hình CNN trên web.

Tóm lại, triển khai mô hình CNN trong nhận dạng tiếng nói là một quá trình đa bước, bao gồm việc tiền xử lý dữ liệu, trích xuất đặc trưng, xây dựng và huấn luyện mô hình, đánh giá và triển khai mô hình. Để triển khai mô hình trên các nền tảng khác nhau, cần sử dụng các công cụ và thư viện hỗ trợ phù hợp.

## Reference
Understanding MFCC better in MFCC-CNN:  
https://www.gosmar.eu/machinelearning/2020/05/25/neural-networks-and-speech-recognition/  
More information  
https://www.sciencedirect.com/science/article/pii/S2214317320302171  
Speech Processing  
https://haythamfayek.com/2016/04/21/speech-processing-for-machine-learning.html