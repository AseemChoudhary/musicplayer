package com.music.musicplayer.mp3player.radio.history;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.music.musicplayer.mp3player.radio.MainRadioHelper;

public class TrackHistoryViewModel extends AndroidViewModel {
    private final TrackHistoryRepository repository;

    public TrackHistoryViewModel(Application application) {
        super(application);

        MainRadioHelper radioDroidApp = getApplication();
        repository = radioDroidApp.getTrackHistoryRepository();
    }

    public LiveData<PagedList<TrackHistoryEntry>> getAllHistoryPaged() {
        return repository.getAllHistoryPaged();
    }
}
