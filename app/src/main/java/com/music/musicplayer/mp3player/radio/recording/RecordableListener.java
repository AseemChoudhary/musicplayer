package com.music.musicplayer.mp3player.radio.recording;

public interface RecordableListener {
    void onBytesAvailable(byte[] buffer, int offset, int length);
    void onRecordingEnded();
}
