package com.zegome.utils.example;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zegome.utils.widget.pagers.ZViewPager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ZViewPager viewPager = (ZViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ImageViewPagerAdapter());
        viewPager.setAni(true);
        viewPager.setSwipe(true);
    }

    class ImageViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final LayoutInflater inflater = (LayoutInflater) MainActivity.this.getLayoutInflater();
            final View view = inflater.inflate(R.layout.item_viewpager_image, null);

            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {

        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }
}
