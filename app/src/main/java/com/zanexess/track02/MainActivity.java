package com.zanexess.track02;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {
    private ListFragment _listFragment;
    private static final String TAG = "ScrollingActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        _listFragment = new ListFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        super.onStart();
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        if (frag == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.main_fragment, _listFragment);
            ft.commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
