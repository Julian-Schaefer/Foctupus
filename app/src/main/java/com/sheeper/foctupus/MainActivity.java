package com.sheeper.foctupus;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;


import com.sheeper.foctupus.game.*;


public class MainActivity extends Activity {

    private MyGLSurfaceView surfaceView;
    //private static AdView adView;
    private static boolean loaded;


    private final Handler handler = new Handler();
    private Runnable hideRunner = new Runnable() {
        @Override
        public void run() {
            hideUI();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if(supportsEs2 || isEmulator()) {
            hideUI();
            LinearLayout surfaceViewContainer = (LinearLayout) findViewById(R.id.mainLayout);
            setupSurface(surfaceViewContainer);
        }
    }


    private void setupSurface(LinearLayout container)
    {
        surfaceView = new MyGLSurfaceView(this, isEmulator());


        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        container.addView(surfaceView, layout);
    }


    /*private void setupAd(LinearLayout container)
    {
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        adView.setVisibility(View.GONE);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (GameManager.getInstance() != null)
                    //showAd();
                ;

                loaded = true;
            }
        });

        LinearLayout.LayoutParams adLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        container.addView(adView, adLayout);
    }*/

    private boolean isEmulator() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"));
    }


    /*public static void showAd()
    {
        Handler refresh = new Handler(Looper.getMainLooper());
        refresh.post(new Runnable() {
            public void run() {
                if (adView != null)
                    adView.setVisibility(View.VISIBLE);
            }
        });
    }

    public static void hideAd()
    {
        Handler refresh = new Handler(Looper.getMainLooper());
        refresh.post(new Runnable() {
            public void run() {
                if (adView != null)
                    adView.setVisibility(View.GONE);
            }
        });
    }*/


    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.onPause();
    }



    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.onResume();

        //loadAd();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            handler.removeCallbacks(hideRunner);
            handler.postDelayed(hideRunner, 200);
            setUIListener();
        }
    }

    private void setUIListener()
    {
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {

                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    handler.removeCallbacks(hideRunner);
                    handler.postDelayed(hideRunner, 100);
                }

            }
        });
    }

    private void hideUI()
    {
        getWindow().getDecorView().setSystemUiVisibility(0);

        if(Build.VERSION.SDK_INT >= 19) {

            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        }
        else if(Build.VERSION.SDK_INT >= 16)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        else if(Build.VERSION.SDK_INT >= 14)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    }


    @Override
    public void onBackPressed() {}

    /*public static void loadAd()
    {
        if(!loaded && adView != null)
        {
            final AdRequest adRequest = new AdRequest.Builder().build();

            Handler refresh = new Handler(Looper.getMainLooper());
            refresh.post(new Runnable() {
                public void run() {
                    adView.loadAd(adRequest);
                }
            });
        }
    }*/

}
