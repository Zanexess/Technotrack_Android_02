package com.zanexess.track02;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashActivity extends Activity {

    private SharedPreferences sPref;

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
            String str = processData.getResult();
            if (str != null) {
                Log.v("Получен результат", str.length()+"");
                saveText(str);
            }
        } else {
            String str = loadText();
            if (str == null) {
                Toast.makeText(getApplicationContext(), "Нет интернет соединения. Приложение закроется через 5 секунд", Toast.LENGTH_SHORT).show();
                finishActivity();
            } else {
                ProcessDataObjects processData = new ProcessDataObjects(SplashActivity.this);
                processData.executeFromExistedResult(str);
            }
        }
    }

    private void finishActivity() {
        Thread timer = new Thread() {
            public void run() {
                try {
                    int logoTimer = 0;
                    while (logoTimer < 2000) {
                        sleep(100);
                        logoTimer = logoTimer + 100;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        timer.start();
    }

    private void saveText(String text) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("SAVED_TEXT", text);
        ed.commit();
    }

    private String loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString("SAVED_TEXT", "");
        return savedText;
    }
}
