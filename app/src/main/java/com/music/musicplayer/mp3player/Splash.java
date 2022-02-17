package com.music.musicplayer.mp3player;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.music.musicplayer.mp3player.R;

import androidx.appcompat.app.AppCompatActivity;



public class Splash extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash_main);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }

}
