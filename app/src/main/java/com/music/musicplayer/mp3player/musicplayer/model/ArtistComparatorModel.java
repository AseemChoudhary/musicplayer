package com.music.musicplayer.mp3player.musicplayer.model;

import java.util.Comparator;

public class ArtistComparatorModel implements Comparator<ArtistModel> {

    @Override
    public int compare(ArtistModel artistModel, ArtistModel artistModel1) {
        return artistModel.getName().toString().compareTo(artistModel1.getName().toString());
    }
}
