package com.templatemela.sangitguru.musicplayer.model;

import java.util.Comparator;

public class LocalMusicComparator implements Comparator<LocalTrackModel> {
    public int compare(LocalTrackModel localTrackModel, LocalTrackModel localTrackModel1) {
        return localTrackModel.getTitle().toString().compareTo(localTrackModel1.getTitle().toString());
    }
}
