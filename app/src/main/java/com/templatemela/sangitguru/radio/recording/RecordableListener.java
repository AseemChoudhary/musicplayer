package com.templatemela.sangitguru.radio.recording;

public interface RecordableListener {
    void onBytesAvailable(byte[] buffer, int offset, int length);
    void onRecordingEnded();
}
