# Using Hidden Markov Model (HMM)

Mô hình Hidden Markov Model (HMM) là một trong những mô hình phổ biến nhất được sử dụng trong nhận dạng tiếng nói. Mô hình HMM được sử dụng để mô hình hóa các chuỗi tín hiệu âm thanh và xác định các từ hoặc câu nói được phát ra từ chuỗi này.

Bước đầu tiên để triển khai mô hình HMM trong nhận dạng tiếng nói là xây dựng một bộ dữ liệu huấn luyện. Bộ dữ liệu này gồm các tập tin âm thanh của các câu nói khác nhau và các tập tin văn bản tương ứng với các câu nói đó. Quá trình huấn luyện sẽ sử dụng các tập tin âm thanh này để học cách xác định các từ hoặc câu nói từ chuỗi tín hiệu âm thanh.

Tiếp theo, ta cần xác định các đặc trưng (feature) của âm thanh. Các đặc trưng này sẽ được sử dụng để tạo ra các ma trận đặc trưng (feature matrix) cho mỗi câu nói trong bộ dữ liệu huấn luyện. Các đặc trưng phổ biến được sử dụng trong nhận dạng tiếng nói bao gồm: tần số cơ bản (pitch), tần số của dải tần số (spectral frequency), và độ dài của âm tiết (duration). Các đặc trưng này được tính toán sử dụng các thuật toán xử lý tín hiệu âm thanh như Fast Fourier Transform (FFT) và Mel-Frequency Cepstral Coefficients (MFCC).

Sau khi có các ma trận đặc trưng cho các câu nói trong bộ dữ liệu huấn luyện, ta sử dụng chúng để huấn luyện mô hình HMM. Quá trình huấn luyện này sử dụng thuật toán Baum-Welch để tối ưu hóa các tham số của mô hình HMM, bao gồm số trạng thái (state), số ký tự, và ma trận xác suất chuyển trạng thái (transition matrix) và ma trận xác suất ký tự (emission matrix).

Sau khi huấn luyện mô hình HMM, ta sử dụng nó để nhận dạng tiếng nói trên các tập dữ liệu mới. Quá trình này bao gồm việc tạo ra ma trận đặc trưng cho mỗi câu nói trong tập dữ liệu mới, sử dụng mô hình HMM để tính toán xác suất của câu nói và sau đó chọn câu có xác suất cao nhất là kết quả của quá trình nhận dạng.

Tuy nhiên, mô hình HMM có một số hạn chế, bao gồm khả năng nhận dạng kém khi đối diện với sự biến động của âm thanh và khả năng xử lý các tiếng đệm (background noise) hay ngữ điệu (prosody) của tiếng nói. Do đó, các kỹ thuật tiên tiến hơn, như Convolutional Neural Networks (CNNs) và Recurrent Neural Networks (RNNs), đang được sử dụng phổ biến hơn trong các ứng dụng nhận dạng tiếng nói.

Tuy nhiên, mô hình HMM vẫn là một trong những công cụ quan trọng trong nhận dạng tiếng nói và được sử dụng rộng rãi trong nhiều ứng dụng, từ nhận dạng giọng nói trong các cuộc gọi đến tự động chuyển văn bản cho người khiếm thính.  
## Reference  
From the student who was guided by my teacher  
https://github.com/tunghia1890/101_HMMSpeechRecognition
