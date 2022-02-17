package com.music.musicplayer.mp3player.radio.interfaces;

import com.music.musicplayer.mp3player.radio.station.StationsFilter;

public interface IFragmentSearchable {
    void Search(StationsFilter.SearchStyle searchStyle, String query);
}
