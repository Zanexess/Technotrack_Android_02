package com.zanexess.track02;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScreenSlidePageFragment extends Fragment {
    private static int _imageSize;
    private static volatile boolean state = false;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView info = (TextView) rootView.findViewById(R.id.info);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.image_item);

        Bundle bundle = getArguments();
        Integer position = bundle.getInt("QQQ");

        _imageSize = getResources().getDisplayMetrics().widthPixels;

        //Для того, чтобы не прыгало изображение, когда подгрузится
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(_imageSize, _imageSize);
        imageView.setLayoutParams(lp);

        TechnologyData.Technology technology = TechnologyData.instance().getImage(position);

        //Загрузка без потери качества
        if (NetworkManager.isNetworkAvailable(getContext())) {
            LoadImageTask.loadBitmap(getContext(), technology.getUrl_picture(), imageView, ScreenSlidePageFragment.this, false);
        } else if (imageView.getDrawable() == null) {
            //Пробуем грузить в плохом разрешении из кэша
            LoadImageTask.loadBitmap(getContext(), technology.getUrl_picture(), imageView, ScreenSlidePageFragment.this, true);
            if (!state) {
                Toast.makeText(getContext(), "Нет интернет соединения для загрузки в большом разрешении. Возможно отображение в плохом качестве.", Toast.LENGTH_SHORT).show();
                state = true;
                Thread timer = new Thread() {
                    public void run() {
                        try {
                            int logoTimer = 0;
                            while (logoTimer < 2100) {
                                sleep(100);
                                logoTimer = logoTimer + 100;
                            }
                            state = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                timer.start();
            }
        }
        title.setText(technology.getTitle());
        if (!technology.getInfo().equals("")) {
            info.setText(technology.getInfo());
        }

        return rootView;
    }
}