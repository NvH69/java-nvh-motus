package com.nvh.motus.service;

import com.nvh.motus.service.ResourceLoader;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SampledSon {

    private AudioFormat format;
    private byte[] samples;

    public SampledSon(String filename) {
        try {
            ResourceLoader resourceLoader = new ResourceLoader();
            AudioInputStream stream = AudioSystem.getAudioInputStream(resourceLoader.getFileFromResource(filename));
            format = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getSamples() {
        return samples;
    }

    private byte[] getSamples(AudioInputStream stream) {
        int length = (int) (stream.getFrameLength() * format.getFrameSize());
        samples = new byte[length];
        DataInputStream in = new DataInputStream(stream);
        try {
            in.readFully(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;
    }

    public void play() {
        InputStream source = new ByteArrayInputStream(this.getSamples());

        int bufferSize = 1000000;
        byte[] buffer = new byte[bufferSize];
        SourceDataLine line;
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, bufferSize);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }
        line.start();
        try {
            int numBytesRead = 0;
            while (numBytesRead != -1) {
                numBytesRead = source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1)
                    line.write(buffer, 0, numBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        line.drain();
        line.close();
    }
}
