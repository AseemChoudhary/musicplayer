package com.music.musicplayer.mp3player;

import static com.music.musicplayer.mp3player.ringtone.utils.MyUtils.ChangeVoiceLocation;
import static com.music.musicplayer.mp3player.ringtone.utils.MyUtils.CutAudioLocation;
import static com.music.musicplayer.mp3player.ringtone.utils.MyUtils.NameLocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.music.musicplayer.mp3player.ringtone.MyNameRingtoneListActivity;
import com.music.musicplayer.mp3player.ringtone.utils.AdsUtils;
import com.music.musicplayer.mp3player.R;

public class MainCreationActivity extends AppCompatActivity  {
    LinearLayout btnVoiceRecorderList;
    LinearLayout btnCutAudioList;
    LinearLayout btnNameRingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_creation);
        AdsUtils.initAd(this);
        AdsUtils.loadLargeBannerAd(this,findViewById(R.id.adsView));
        btnNameRingList=findViewById(R.id.btnNameRingList);
        btnCutAudioList=findViewById(R.id.btnCutAudioList);
        btnVoiceRecorderList=findViewById(R.id.btnVoiceRecorderList);
        btnVoiceRecorderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainCreationActivity.this, MyNameRingtoneListActivity.class);
                intent.putExtra("ListPath", ChangeVoiceLocation.getAbsolutePath());
                intent.putExtra("name", "My Voice Creation");
                startActivity(intent);
            }
        });
        btnCutAudioList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainCreationActivity.this, MyNameRingtoneListActivity.class);
                intent.putExtra("ListPath", CutAudioLocation.getAbsolutePath());
                intent.putExtra("name", "My Mp3 Cut Creation");
                startActivity(intent);
            }
        });
        btnNameRingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainCreationActivity.this, MyNameRingtoneListActivity.class);
                intent.putExtra("ListPath", NameLocation.getAbsolutePath());
                intent.putExtra("name", "My Name Ringtone Creation");
                startActivity(intent);
            }
        });

    }
}