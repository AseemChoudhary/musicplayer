package com.templatemela.sangitguru.musicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.templatemela.sangitguru.MainActivity;
import com.templatemela.sangitguru.musicplayer.adapter.ArtistAdapter;

import com.templatemela.sangitguru.R;

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
