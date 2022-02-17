package com.music.musicplayer.mp3player.musicplayer.scene;

import android.content.Context;

import com.music.musicplayer.mp3player.R;
import com.music.musicplayer.mp3player.musicplayer.render.GLScene;
import com.music.musicplayer.mp3player.musicplayer.render.MyGLUtils;

public class CommonScene extends GLScene {
    private int mAudioTextureId;
    private int mProgram;
    private int mTextTureWidth;

    public CommonScene(Context context, int mAudioTextureId, int mTextTureWidth, int i3) {
        mProgram = MyGLUtils.buildProgram(context, R.raw.vertext, i3);
        this.mAudioTextureId = mAudioTextureId;
        this.mTextTureWidth = mTextTureWidth;
    }

    @Override
    public void onDraw(int i, int i2) {
        runShandertoyProgram(mProgram, new int[]{i, i2}, new int[]{mAudioTextureId}, new int[][]{new int[]{mTextTureWidth, 2}});
    }
}
