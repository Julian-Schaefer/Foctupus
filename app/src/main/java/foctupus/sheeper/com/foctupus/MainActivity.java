package foctupus.sheeper.com.foctupus;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import foctupus.sheeper.com.foctupus.game.MyGLSurfaceView;
import foctupus.sheeper.com.foctupus.game.logic.GameManager;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;

public class MainActivity extends Activity {

    private MyGLSurfaceView surfaceView;
    private static AdView adView;
    private static boolean loaded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;


        setUIListener();

        if(supportsEs2 || isEmulator()) {

            LinearLayout surfaceViewContainer = (LinearLayout) findViewById(R.id.mainLayout);

            setupSurface(surfaceViewContainer);

            setupAd(surfaceViewContainer);

        }


        Log.i("Testdas", "Activity onCreate");

    }


    private void setupSurface(LinearLayout container)
    {
        surfaceView = new MyGLSurfaceView(this, isEmulator());


        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        container.addView(surfaceView, layout);
    }


    private void setupAd(LinearLayout container)
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
    }

    private boolean isEmulator() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"));
    }


    public static void showAd()
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
    }


    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.onPause();
        Log.i("Testdas", "Activity onPause");

    }


    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.onResume();

        hideUI();


        loadAd();

    }

    private void setUIListener()
    {
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {

                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                if (android.os.Build.VERSION.SDK_INT >= 19)
                                    immersiveMode();
                                else if (android.os.Build.VERSION.SDK_INT >= 14)
                                    lightsOut();
                            } catch (Exception e) {
                            }
                        }

                    });
                }

            }
        });
    }

    private void hideUI()
    {
       // if (android.os.Build.VERSION.SDK_INT >= 19)
       //     immersiveMode();
       // else if (android.os.Build.VERSION.SDK_INT >= 14)
       //    lightsOut();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                } catch (Exception e) {
                }
            }
        });
    }


    private void lightsOut()
    {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                } catch (Exception e) {
                }
            }
        });
    }

    private void immersiveMode()
    {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                } catch (Exception e) {
                }
            }
        });
    }


    @Override
    public void onBackPressed() {}

    public static void loadAd()
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
    }

}
