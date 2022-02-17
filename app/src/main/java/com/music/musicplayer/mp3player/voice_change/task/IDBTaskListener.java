package com.music.musicplayer.mp3player.voice_change.task;

public interface IDBTaskListener {
    void onDoInBackground();

    void onPostExecute();

    void onPreExecute();
}
