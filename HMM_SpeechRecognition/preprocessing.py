import math

import librosa
import numpy as np
from pydub import AudioSegment
import librosa
import wave
def remove_silence(audio_file):
    # Mở file âm thanh và đọc các thông số
    with wave.open(audio_file, 'rb') as wav:
        frames = wav.readframes(-1)
        rate = wav.getframerate()
        channels = wav.getnchannels()
        sample_width = wav.getsampwidth()
        num_frames = wav.getnframes()

    # Chuyển đổi byte thành mảng số nguyên
    frames = np.frombuffer(frames, dtype=np.int16)
    # Tìm các mẫu âm thanh có giá trị vượt quá ngưỡng
    threshold = 500  # ngưỡng giá trị
    silence_mask = abs(frames) < threshold
    silence_starts, = np.where(np.diff(silence_mask.astype(int)) == 1)
    silence_ends, = np.where(np.diff(silence_mask.astype(int)) == -1)

    # Thêm giá trị đầu tiên và cuối cùng vào danh sách
    if silence_starts[0] > silence_ends[0]:
        silence_starts = np.concatenate([[0], silence_starts])
    if silence_starts[-1] > silence_ends[-1]:
        silence_ends = np.concatenate([silence_ends, [num_frames]])

    # Tìm khoảng lặng ở đầu và cuối file
    if len(silence_starts) > 0 and silence_starts[0] == 0:
        start_idx = silence_ends[0]
    else:
        start_idx = 0
    if len(silence_ends) > 0 and silence_ends[-1] == num_frames:
        end_idx = silence_starts[-1]
    else:
        end_idx = num_frames
    silence_start_after_end = []
    if end_idx < num_frames:
        silence_start_after_end, = np.where(silence_starts > end_idx)
    if len(silence_start_after_end) > 0:
        end_idx = silence_starts[silence_start_after_end[0]]
    # Bỏ đi khoảng lặng ở đầu và cuối file
    if silence_starts[0] < silence_ends[0]:
        start_idx = silence_ends[0]
    if silence_starts[-1] < silence_ends[-1]:
        end_idx = silence_starts[-1]

    # Chuyển đổi mảng số nguyên thành byte và lưu lại file mới
    new_frames = frames[start_idx:end_idx].astype(np.int16)
    with wave.open('temp/trimmed.wav', 'wb') as wav:
        wav.setparams((channels, sample_width, rate, len(new_frames), "NONE", "Uncompressed"))
        wav.writeframes(new_frames.tobytes())
def get_mfcc(file_path):
    remove_silence(file_path)
    y, sr = librosa.load('temp/trimmed.wav')  # read .wav file
    hop_length = math.floor(sr * 0.010)  # 10ms hop
    win_length = math.floor(sr * 0.025)  # 25ms frame
    # mfcc is 12 x T matrix
    mfcc = librosa.feature.mfcc(
        y=y, sr=sr, n_mfcc=12, n_fft=1024,
        hop_length=hop_length, win_length=win_length)
    # subtract mean from mfcc --> normalize mfcc
    mfcc = mfcc - np.mean(mfcc, axis=1).reshape((-1, 1))
    # delta feature 1st order and 2nd order
    delta1 = librosa.feature.delta(mfcc, order=1)
    delta2 = librosa.feature.delta(mfcc, order=2)
    # X is 36 x T
    X = np.concatenate([mfcc, delta1, delta2], axis=0)  # O^r
    # return T x 36 (transpose of X)
    return X.T  # hmmlearn use T x N matrix
