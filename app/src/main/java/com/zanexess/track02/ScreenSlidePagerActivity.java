package com.zanexess.track02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;

public class ScreenSlidePagerActivity extends AppCompatActivity {
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(getIntent().getExtras().getInt("Int"));
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    //Возвращение на позицию где закончили. Будет внизу
    @Override
    public void onBackPressed() {
        finishWithResult();
    }

    // Переопределение кнопки назад в ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishWithResult();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Завершить активити, вернув результат
    private void finishWithResult()
    {
        Bundle conData = new Bundle();
        conData.putInt("param_result", mPager.getCurrentItem());
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ScreenSlidePageFragment screenSlidePageFragment = new ScreenSlidePageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("QQQ", position);
            screenSlidePageFragment.setArguments(bundle);
            return screenSlidePageFragment;
        }
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getSupportActionBar() != null) {
                //Заголовок
                getSupportActionBar().setTitle(TechnologyData.instance().getImage(position).getTitle());
            }
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getCount() {
            return TechnologyData.instance().getImages().size();
        }
    }


}