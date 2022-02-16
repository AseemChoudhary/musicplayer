package com.templatemela.sangitguru.musicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.templatemela.sangitguru.MainActivity;
import com.templatemela.sangitguru.musicplayer.adapter.SongsAdapter;

import com.templatemela.sangitguru.R;

public class FragmentSongs extends Fragment {
    RecyclerView recyclerview;
    SongsAdapter songsAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_songs, viewGroup, false);
        recyclerview = (RecyclerView) inflate.findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 1));
        return inflate;
    }

    @Override
    public void onResume() {
         songsAdapter = new SongsAdapter(getContext(), MainActivity.finalLocalSearchResultList);
         recyclerview.setAdapter(this.songsAdapter);
         super.onResume();
    }
}
