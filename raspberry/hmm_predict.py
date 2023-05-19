
import pickle
import wave

import numpy as np
import pyaudio
from pydub import AudioSegment
import preprocessing

import os
class HMMRecognition:
    def __init__(self):
        self.model = {}

        self.class_names = ['batden', 'tatden', 'batquat','tatquat','mocua','dongcua','batdieuhoa','tatdieuhoa','tangtocdoquat','giamtocdoquat']
        self.audio_format = 'wav'

        self.record_path = 'temp/record.wav'
        self.trimmed_path = 'temp/trimmed.wav'
        self.model_path = 'models_train'

        self.load_model()

    # @staticmethod
    def load_model(self):
        for key in self.class_names:
            name = f"{self.model_path}/model_{key}.pkl"
            with open(name, 'rb') as file:
                self.model[key] = pickle.load(file)

    def predict(self, file_name=None):
        if not file_name:
            file_name = self.record_path

        # Predict
        record_mfcc = preprocessing.get_mfcc(file_name)
        scores = [self.model[cname].score(record_mfcc) for cname in self.class_names]
        print('scores', scores)
        predict_word = np.argmax(scores)
        print(self.class_names[predict_word])
        return self.class_names[predict_word]
    def record(self):
        CHUNK = 1024
        FORMAT = pyaudio.paInt16
        CHANNELS = 1
        RATE = 16000
        RECORD_SECONDS = 3

        p = pyaudio.PyAudio()

        stream = p.open(format=FORMAT,
                        channels=CHANNELS,
                        rate=RATE,
                        input=True,
                        frames_per_buffer=CHUNK)

        frames = []
        input("Press Enter to record...")
        print('recording ...')
        for i in range(0, int(RATE / CHUNK * RECORD_SECONDS)):
            data = stream.read(CHUNK)
            frames.append(data)

        print('stopped record!')
        stream.stop_stream()
        stream.close()
        p.terminate()

        wf = wave.open(self.record_path, 'wb')
        wf.setnchannels(CHANNELS)
        wf.setsampwidth(p.get_sample_size(FORMAT))
        wf.setframerate(RATE)
        wf.writeframes(b''.join(frames))
        wf.close()


if __name__ == '__main__':
    hmm_reg = HMMRecognition()
    hmm_reg.predict("new_audio.wav")
    
    