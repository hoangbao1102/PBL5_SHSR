import pickle
import wave

import numpy as np
import pyaudio
from pydub import AudioSegment
import preprocessing
from hmm_predict import HMMRecognition
import os




hmm_reg = HMMRecognition()
string=hmm_reg.predict("new_audio.wav")
print(string)
