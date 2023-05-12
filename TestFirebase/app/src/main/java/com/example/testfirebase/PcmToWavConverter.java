package com.example.testfirebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.sauronsoftware.jave.*;

public class PcmToWavConverter {
    public static void convertPcmToWav(File inputFile, File outputFile) throws IOException, EncoderException {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le");
        audio.setBitRate(new Integer(256000));
        audio.setChannels(new Integer(1));
        audio.setSamplingRate(new Integer(16000));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("wav");
        attrs.setAudioAttributes(audio);
        List<String> cmd = new ArrayList<String>();
        cmd.add("-vn");
        cmd.add("-f");
        cmd.add("s16le");
        cmd.add("-ac");
        cmd.add("1");
        cmd.add("-ar");
        cmd.add("16000");
        cmd.add("-i");
        cmd.add(inputFile.getAbsolutePath());
        cmd.add(outputFile.getAbsolutePath());
        Encoder encoder = new Encoder();
        encoder.encode(inputFile, outputFile, attrs);
    }
}
