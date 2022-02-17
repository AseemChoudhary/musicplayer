package com.music.musicplayer.mp3player.radio.recording;

import androidx.annotation.NonNull;

import java.util.Map;

public interface Recordable {

    boolean canRecord();

    void startRecording(@NonNull RecordableListener recordableListener);

    void stopRecording();

    boolean isRecording();

    Map<String, String> getRecordNameFormattingArgs();
    String getExtension();
}
