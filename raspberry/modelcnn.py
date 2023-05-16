import tensorflow as tf
from pydub import AudioSegment
from pydub.silence import detect_nonsilent
import numpy as np
import pyaudio
import wave

def record():
    CHUNK = 1024
    FORMAT = pyaudio.paInt16
    CHANNELS = 1
    RATE = 16000
    RECORD_SECONDS = 6
    WAVE_OUTPUT_FILENAME = "new_audio.wav"

    p = pyaudio.PyAudio()



    frames = []


    print('reading audio file...')
    with wave.open('new_audio.wav', 'rb') as wf:
        for i in range(0, int(RATE / CHUNK * RECORD_SECONDS)):
            data = wf.readframes(CHUNK)
            frames.append(data)



    print('audio file read!')

    p.terminate()

    wf = wave.open(WAVE_OUTPUT_FILENAME, 'wb')
    wf.setnchannels(CHANNELS)
    wf.setsampwidth(p.get_sample_size(FORMAT))
    wf.setframerate(RATE)
    wf.writeframes(b''.join(frames))
    wf.close()
def get_spectrogram(waveform):
      # Zero-padding for an audio waveform with less than 16,000 samples.
  input_len = 32000
  waveform = waveform[:input_len]
  # Cast the waveform tensors' dtype to float32.
  waveform = tf.cast(waveform, dtype=tf.float32)
  # Convert the waveform to a spectrogram via a STFT.
  spectrogram = tf.signal.stft(
      waveform, frame_length=256, frame_step=128)
  # Obtain the magnitude of the STFT.
  spectrogram = tf.abs(spectrogram)
  # Add a `channels` dimension, so that the spectrogram can be used
  # as image-like input data with convolution layers (which expect
  # shape (`batch_size`, `height`, `width`, `channels`).
  spectrogram = spectrogram[..., tf.newaxis]
  # Resize the spectrogram to a fixed size (e.g., 224x224).
  spectrogram = tf.image.resize(spectrogram, size=(240, 240))
  return spectrogram
def decode_audio(audio_binary):
      # Decode WAV-encoded audio files to `float32` tensors, normalized
  # to the [-1.0, 1.0] range. Return `float32` audio and a sample rate.
  audio, _ = tf.audio.decode_wav(contents=audio_binary)
  # Since all the data is single channel (mono), drop the `channels`
  # axis from the array.
  return tf.squeeze(audio, axis=-1)
def remove_sil(path_in, path_out, format="wav"):
    sound = AudioSegment.from_file(path_in, format=format)
    non_sil_times = detect_nonsilent(sound, min_silence_len=50, silence_thresh=sound.dBFS * 1.5)
    if len(non_sil_times) > 0:
        non_sil_times_concat = [non_sil_times[0]]
        if len(non_sil_times) > 1:
            for t in non_sil_times[1:]:
                if t[0] - non_sil_times_concat[-1][-1] < 50:
                    non_sil_times_concat[-1][-1] = t[1]
                else:
                    non_sil_times_concat.append(t)
        non_sil_times = [t for t in non_sil_times_concat if t[1] - t[0] > 80]
        sound[non_sil_times[0][0]: non_sil_times[-1][1]].export(path_out, format='wav')

def predict():
    model = tf.keras.models.load_model('CNN.h5')
    string = ['batden', 'batdieuhoa', 'batquat', 'dongcua', 'giamtocdoquat', 'mocua', 'tangtocdoquat', 'tatden', 'tatdieuhoa', 'tatquat']
    commands = np.array(string)
    record()

    # remove_silence(WAVE_OUTPUT_FILENAME)
    remove_sil("new_audio.wav","audio.wav")

    # Đọc file âm thanh đã cắt
    new_audio_binary = tf.io.read_file("audio.wav")
    new_waveform = decode_audio(new_audio_binary)
    new_spectrogram = get_spectrogram(new_waveform)
    new_spectrogram = tf.expand_dims(new_spectrogram, axis=0)
    predictions = model.predict(new_spectrogram)

    # Chuyển đổi các xác suất thành nhãn
    predicted_label_id = np.argmax(predictions[0])
    predicted_label = commands[predicted_label_id]

    print('Predicted label:', predicted_label)
    return predicted_label
    
