package com.music.musicplayer.mp3player.musicplayer.model;

import java.util.ArrayList;
import java.util.List;

public class FavouriteModel {
    private List<UnifiedTrackModel> favourite = new ArrayList();

    public List<UnifiedTrackModel> getFavourite() {
        return favourite;
    }

    public void setFavourite(List<UnifiedTrackModel> favourite) {
        this.favourite = favourite;
    }
}
