package foctupus.sheeper.com.foctupus.game.logic;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import foctupus.sheeper.com.foctupus.R;
import foctupus.sheeper.com.foctupus.game.FoctupusDatabase;
import foctupus.sheeper.com.foctupus.engine.renderer.Loader;

/**
 * Created by julianschafer on 23.04.16.
 */

public class SoundPlayer {

    private SoundPool soundPool;
    private AudioManager audioManager;
    private FoctupusDatabase database;

    private Context context;

    private int loaded = 0;
    private int cutSoundID;

    private static SoundPlayer instance;

    public static SoundPlayer getInstance()
    {
        if(instance == null)
            instance = new SoundPlayer();

        return instance;
    }

    private SoundPlayer()
    {
        context = Loader.getContext();

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded++;
            }
        });

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        database = FoctupusDatabase.getInstance();

        loadSounds();
    }

    private void loadSounds()
    {
        cutSoundID = soundPool.load(context, R.raw.swipe, 1);
    }

    public void playCutSound()
    {
        if (loaded == 1 && database.isSoundEnabled()) {
            int actualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

            float volume = (float) actualVolume / (float) maxVolume;
            soundPool.play(cutSoundID, volume, volume, 1, 0, 1f);
        }
    }

}
