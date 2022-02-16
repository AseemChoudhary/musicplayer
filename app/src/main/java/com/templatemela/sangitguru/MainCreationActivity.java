package com.templatemela.sangitguru;

import static com.templatemela.sangitguru.ringtone.utils.MyUtils.ChangeVoiceLocation;
import static com.templatemela.sangitguru.ringtone.utils.MyUtils.CutAudioLocation;
import static com.templatemela.sangitguru.ringtone.utils.MyUtils.NameLocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.templatemela.sangitguru.ringtone.MyNameRingtoneListActivity;
import com.templatemela.sangitguru.ringtone.utils.AdsUtils;

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