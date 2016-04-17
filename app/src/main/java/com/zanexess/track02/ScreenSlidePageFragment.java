package com.zanexess.track02;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {
    final static String DOMEN = "http://mobevo.ext.terrhq.ru/";

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

        String url = DOMEN + TechnologyData.instance().getImage(position).getUrl_picture();
        Bitmap bm = ListFragment.getBitmapFromMemCache(url);
        imageView.setImageBitmap(bm);

        title.setText(TechnologyData.instance().getImage(position).getTitle());
        if (!TechnologyData.instance().getImage(position).getInfo().equals("")) {
            info.setText(TechnologyData.instance().getImage(position).getInfo());
        }

        return rootView;
    }
}