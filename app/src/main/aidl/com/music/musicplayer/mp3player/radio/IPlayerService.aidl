package com.music.musicplayer.mp3player.radio;

import com.music.musicplayer.mp3player.radio.service.PauseReason;
import com.music.musicplayer.mp3player.radio.station.DataRadioStation;
import com.music.musicplayer.mp3player.radio.station.live.StreamLiveInfo;
import com.music.musicplayer.mp3player.radio.station.live.ShoutcastInfo;
import com.music.musicplayer.mp3player.radio.players.PlayState;
import com.music.musicplayer.mp3player.radio.players.selector.PlayerType;
import android.support.v4.media.session.MediaSessionCompat;

interface IPlayerService
{
void SetStation(in DataRadioStation station);
void Play(boolean isAlarm);
void Pause(in PauseReason pauseReason);
void Resume();
void Stop();
void SkipToNext();
void SkipToPrevious();
void addTimer(int secondsAdd);
void clearTimer();
long getTimerSeconds();
String getCurrentStationID();
DataRadioStation getCurrentStation();
StreamLiveInfo getMetadataLive();
ShoutcastInfo getShoutcastInfo();
MediaSessionCompat.Token getMediaSessionToken();
boolean isPlaying();
PlayState getPlayerState();
void startRecording();
void stopRecording();
boolean isRecording();
String getCurrentRecordFileName();
long getTransferredBytes();
long getBufferedSeconds();
long getLastPlayStartTime();
boolean getIsHls();
PauseReason getPauseReason();
boolean isNotificationActive();

void enableMPD(String hostname, int port);
void disableMPD();

void warnAboutMeteredConnection(in PlayerType playerType);
}
