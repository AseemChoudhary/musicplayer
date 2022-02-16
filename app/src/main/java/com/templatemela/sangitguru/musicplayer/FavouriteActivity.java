package com.templatemela.sangitguru.musicplayer;
import static com.templatemela.sangitguru.MainActivity.favouriteTracks;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.templatemela.sangitguru.R;
import com.templatemela.sangitguru.musicplayer.adapter.FavouriteAdapter;
import com.templatemela.sangitguru.musicplayer.model.FavouriteModel;
import com.templatemela.sangitguru.musicplayer.model.LocalTrackModel;
import com.templatemela.sangitguru.ringtone.utils.AdsUtils;

public class FavouriteActivity extends AppCompatActivity {
    private FrameLayout adContainerView;
    ImageView back;
    FavouriteAdapter favouriteAdapter;
    RecyclerView recyclerView;
    private List<LocalTrackModel> songListTrack;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_favourite);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        songListTrack = new ArrayList();
        AdsUtils.initAd(this);
        AdsUtils.loadLargeBannerAd(this,findViewById(R.id.adsView));
        back = (ImageView) findViewById(R.id.back);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        if (favouriteTracks == null) {
            favouriteTracks = new FavouriteModel();
        }
        for (int i = 0; i < favouriteTracks.getFavourite().size(); i++) {
            songListTrack.add(favouriteTracks.getFavourite().get(i).getLocalTrack());
        }
        favouriteAdapter = new FavouriteAdapter(this,favouriteTracks.getFavourite(), songListTrack);
        recyclerView.setAdapter(favouriteAdapter);
        back.setOnClickListener(view -> onBackPressed());
    }



}
