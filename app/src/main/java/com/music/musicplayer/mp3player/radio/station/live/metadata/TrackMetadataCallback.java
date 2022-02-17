package com.music.musicplayer.mp3player.radio.station.live.metadata;


import androidx.annotation.NonNull;

public interface TrackMetadataCallback {
    enum FailureType {
        RECOVERABLE,
        UNRECOVERABLE,
    }

    void onFailure(@NonNull FailureType failureType);
    void onSuccess(@NonNull TrackMetadata trackMetadata);
}
