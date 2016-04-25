package com.zanexess.track02;

import android.app.Activity;
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
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        setContentView(R.layout.splash_activity);
        if (NetworkManager.isNetworkAvailable(getApplicationContext())) {
            ProcessDataObjects processData = new ProcessDataObjects(SplashActivity.this);
            processData.execute();
        } else {
            Toast.makeText(getApplicationContext(), "Нет интернет соединения. Приложение закроется через 5 секунд", Toast.LENGTH_SHORT).show();
            finishActivity();
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
