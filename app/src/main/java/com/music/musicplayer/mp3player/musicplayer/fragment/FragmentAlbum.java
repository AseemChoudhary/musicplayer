package com.music.musicplayer.mp3player.musicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.music.musicplayer.mp3player.MainActivity;
import com.music.musicplayer.mp3player.musicplayer.adapter.AlbumAdapter;

import com.music.musicplayer.mp3player.R;

public class FragmentAlbum extends Fragment {
    AlbumAdapter albumAdapter;
    RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_album, viewGroup, false);
        recyclerview = (RecyclerView) inflate.findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        albumAdapter = new AlbumAdapter(getContext(), MainActivity.finalAlbums);
        recyclerview.setAdapter(albumAdapter);
        return inflate;
    }
}
