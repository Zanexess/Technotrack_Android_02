package com.zanexess.track02;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class ListFragment extends Fragment {
    private static int _imageSize;
    private RecyclerView.LayoutManager mLayoutManager;
    final static String DOMEN = "http://mobevo.ext.terrhq.ru/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.technology_list, container, false);
        if (null == root) return null;

        // Высчитываем оптимальные для текущего экрана размеры
        _imageSize = LoadImageTask.updateImageSize(getResources().getDisplayMetrics());

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        MyAdapter wa = new MyAdapter(
                getActivity(),
                R.layout.technology_list_item,
                TechnologyData.instance().getImages()
        );
        mRecyclerView.setAdapter(wa);
        return root;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        TextView _tvw;
        TextView _twi;
        ImageView _iv;
        View _card;
        int _pos;

        public ViewHolder(View itemView) {
            super(itemView);
            _card = itemView;
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

        Context _context;
        int _resource;
        List<TechnologyData.Technology> _data;

        public MyAdapter(Context context, int resource, List<TechnologyData.Technology> objects) {
            _context = context;
            _data = objects;
            _resource = resource;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View convertView = inflater.inflate(R.layout.technology_list_item, parent, false);
            ViewHolder holder = new ViewHolder(convertView);
            assert convertView != null;
            holder._tvw = (TextView)convertView.findViewById(R.id.title_text);
            holder._twi = (TextView)convertView.findViewById(R.id.info_text);
            holder._iv = (ImageView)convertView.findViewById(R.id.image);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(_imageSize, _imageSize);
            holder._iv.setLayoutParams(lp);

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
//            TechnologyData.Technology technology = list.get(position);
            TechnologyData.Technology technology = TechnologyData.instance().getImage(position);
            if (technology == null) return;
            holder._pos = position;
            holder._tvw.setText(technology.getTitle());
            holder._twi.setText(technology.getInfo());
            final int i = position;
            holder._card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((MainActivity)getActivity()).changeFragment(position);
                    Intent intent = new Intent(getActivity(), ScreenSlidePagerActivity.class);
                    intent.putExtra("Int", position);
                    startActivityForResult(intent, 90);
                }
            });
            LoadImageTask.loadBitmap(getActivity(), technology.getUrl_picture(), holder._iv, ListFragment.this, true);
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 90:
                Bundle res = data.getExtras();
                Integer result = res.getInt("param_result");
                mLayoutManager.scrollToPosition(result);
                break;
        }

    }
}

