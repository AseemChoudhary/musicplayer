package com.music.musicplayer.mp3player.radio.players.mediaplayer;

import com.music.musicplayer.mp3player.radio.station.live.ShoutcastInfo;
import com.music.musicplayer.mp3player.radio.station.live.StreamLiveInfo;

interface StreamProxyListener {
    void onFoundShoutcastStream(ShoutcastInfo bitrate, boolean isHls);
    void onFoundLiveStreamInfo(StreamLiveInfo liveInfo);
    void onStreamCreated(String proxyConnection);
    void onStreamStopped();
    void onBytesRead(byte[] buffer, int offset, int length);
}
