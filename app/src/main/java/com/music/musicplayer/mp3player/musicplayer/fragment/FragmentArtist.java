package com.music.musicplayer.mp3player.musicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.music.musicplayer.mp3player.MainActivity;
import com.music.musicplayer.mp3player.musicplayer.adapter.ArtistAdapter;

import com.music.musicplayer.mp3player.R;

public class FragmentArtist extends Fragment {
    ArtistAdapter artistAdapter;
    RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_artist, viewGroup, false);
        recyclerview = (RecyclerView) inflate.findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        artistAdapter = new ArtistAdapter(getContext(), MainActivity.finalArtists);
        recyclerview.setAdapter(artistAdapter);
        return inflate;
    }
}
