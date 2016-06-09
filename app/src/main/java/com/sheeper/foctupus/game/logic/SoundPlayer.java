package com.sheeper.foctupus.game.logic;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.sheeper.foctupus.R;
import com.sheeper.foctupus.engine.renderer.Loader;
import com.sheeper.foctupus.game.FoctupusDatabase;

/**
 * Created by julianschafer on 23.04.16.
 */

public class SoundPlayer {

    private static SoundPool soundPool;
    private static int loaded = 0;
    private static int cutSoundID;

    private AudioManager audioManager;

    private Context context;

    private boolean muted;

    public SoundPlayer()
    {
        context = Loader.getContext();

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if(soundPool == null)
        {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
                {
                    loaded++;
                }
            });

            loadSounds();
        }

        if(FoctupusDatabase.getInstance().isSoundEnabled())
            muted = false;
        else
            muted = true;
    }

    private void loadSounds()
    {
        cutSoundID = soundPool.load(context, R.raw.swipe, 1);
    }

    public void playCutSound()
    {
        if (loaded == 1 && !muted) {
            int actualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

            float volume = (float) actualVolume / (float) maxVolume;
            soundPool.play(cutSoundID, volume, volume, 1, 0, 1f);
        }
    }

}
