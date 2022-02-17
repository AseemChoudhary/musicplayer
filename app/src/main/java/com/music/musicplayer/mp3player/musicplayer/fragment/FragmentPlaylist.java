package com.music.musicplayer.mp3player.musicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.music.musicplayer.mp3player.MainActivity;
import com.music.musicplayer.mp3player.musicplayer.adapter.PlaylistAdapter;
import com.music.musicplayer.mp3player.musicplayer.FavouriteActivity;
import com.music.musicplayer.mp3player.musicplayer.NewPlaylistActivity;
import com.music.musicplayer.mp3player.R;
import com.music.musicplayer.mp3player.musicplayer.interfaces.OnRefreshViewListner;

public class FragmentPlaylist extends Fragment implements OnRefreshViewListner {
    LinearLayout favList;
    LinearLayout newPlaylist;
    LinearLayout noPlaylist;
    PlaylistAdapter playlistAdapter;
    RecyclerView recyclerview;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_playlist, viewGroup, false);
        recyclerview = (RecyclerView) inflate.findViewById(R.id.recyclerview);
        newPlaylist = (LinearLayout) inflate.findViewById(R.id.newPlaylist);
        noPlaylist = (LinearLayout) inflate.findViewById(R.id.noPlaylist);
        favList = (LinearLayout) inflate.findViewById(R.id.favList);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 1));
        playlistAdapter = new PlaylistAdapter(getContext(), MainActivity.allPlaylists.getPlaylists(),this);
        recyclerview.setAdapter(playlistAdapter);
        newPlaylist.setOnClickListener(view -> startActivity(new Intent(getContext(), NewPlaylistActivity.class)));
        favList.setOnClickListener(view -> startActivity(new Intent(getContext(), FavouriteActivity.class)));
        if (MainActivity.allPlaylists.getPlaylists().size()<=0)
        {
            noPlaylist.setVisibility(View.VISIBLE);
        }
        else {
            noPlaylist.setVisibility(View.GONE);
        }
        return inflate;
    }

    @Override
    public void onResume() {
        if (MainActivity.allPlaylists.getPlaylists().size()<=0)
        {
            noPlaylist.setVisibility(View.VISIBLE);
        }
        else {
            noPlaylist.setVisibility(View.GONE);
        }
        playlistAdapter = new PlaylistAdapter(getContext(), MainActivity.allPlaylists.getPlaylists(),this);
        recyclerview.setAdapter(this.playlistAdapter);
        super.onResume();
    }

    @Override
    public void refreshView() {
        if (MainActivity.allPlaylists.getPlaylists().size()<=0)
        {
            noPlaylist.setVisibility(View.VISIBLE);
        }
        else {
            noPlaylist.setVisibility(View.GONE);
        }
    }
}
