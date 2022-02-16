package com.templatemela.sangitguru.radio.players.mediaplayer;

import com.templatemela.sangitguru.radio.station.live.ShoutcastInfo;
import com.templatemela.sangitguru.radio.station.live.StreamLiveInfo;

interface StreamProxyListener {
    void onFoundShoutcastStream(ShoutcastInfo bitrate, boolean isHls);
    void onFoundLiveStreamInfo(StreamLiveInfo liveInfo);
    void onStreamCreated(String proxyConnection);
    void onStreamStopped();
    void onBytesRead(byte[] buffer, int offset, int length);
}
