package com.zanexess.track02;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        setContentView(R.layout.splash_activity);
        if (chechConnection()) {
            //TODO Splash
            ProcessDataObjects processData = new ProcessDataObjects(SplashActivity.this);
            processData.execute();
        } else {
            finishActivity();
        }
        //finishActivity();
    }

    private boolean chechConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
//            Toast connectedToast = Toast.makeText(getApplicationContext(), "Network connected!", Toast.LENGTH_LONG);
//            connectedToast.show();
            return true;
        } else {
            Toast disconnectedToast = Toast.makeText(getApplicationContext(), "No network connection! Application will be terminated in 5 sec.", Toast.LENGTH_LONG);
            disconnectedToast.show();
            return false;
        }
    }

    private void finishActivity() {
        Thread timer = new Thread() {
            public void run() {
                try {
                    int logoTimer = 0;
                    while (logoTimer < 5000) {
                        sleep(100);
                        logoTimer = logoTimer + 100;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
                System.exit(1);
            }
        };
        timer.start();
    }
}
