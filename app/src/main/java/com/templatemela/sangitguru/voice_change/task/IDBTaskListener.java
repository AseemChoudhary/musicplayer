package com.templatemela.sangitguru.voice_change.task;

public interface IDBTaskListener {
    void onDoInBackground();

    void onPostExecute();

    void onPreExecute();
}
