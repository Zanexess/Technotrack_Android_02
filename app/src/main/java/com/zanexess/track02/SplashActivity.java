package com.zanexess.track02;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        if (chechConnection()) {
            //TODO Splash
        }
        //introActivity();
    }

    private boolean chechConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast connectedToast = Toast.makeText(getApplicationContext(), "Network connected!", Toast.LENGTH_LONG);
            connectedToast.show();
            return true;
        } else {
            Toast disconnectedToast = Toast.makeText(getApplicationContext(), "No network connection!", Toast.LENGTH_LONG);
            disconnectedToast.show();
            return false;
        }
    }

    private void introActivity() {
        Thread timer = new Thread() {
            public void run() {
                try {
                    int logoTimer = 0;
                    while (logoTimer < 2000) {
                        sleep(100);
                        logoTimer = logoTimer + 100;
                    }
                    ;
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        timer.start();
    }
}
