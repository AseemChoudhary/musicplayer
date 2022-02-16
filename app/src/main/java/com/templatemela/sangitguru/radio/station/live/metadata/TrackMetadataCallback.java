package com.templatemela.sangitguru.radio.station.live.metadata;


import androidx.annotation.NonNull;

public interface TrackMetadataCallback {
    enum FailureType {
        RECOVERABLE,
        UNRECOVERABLE,
    }

    void onFailure(@NonNull FailureType failureType);
    void onSuccess(@NonNull TrackMetadata trackMetadata);
}
