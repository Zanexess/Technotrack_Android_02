package com.zanexess.track02;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {
    private static int _imageSize;

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

        // Высчитываем оптимальные для текущего экрана размеры
        _imageSize = getResources().getDisplayMetrics().widthPixels;

        //Для того, чтобы не прыгало изображение, когда подгрузится
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(_imageSize, _imageSize);
        imageView.setLayoutParams(lp);

        TechnologyData.Technology technology = TechnologyData.instance().getImage(position);

        //Загрузка без потери качества
        LoadImageTask.loadBitmap(getContext(), technology.getUrl_picture(), imageView, ScreenSlidePageFragment.this, false);

        title.setText(technology.getTitle());
        if (!technology.getInfo().equals("")) {
            info.setText(technology.getInfo());
        }

        return rootView;
    }
}