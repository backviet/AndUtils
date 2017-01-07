package com.zegome.utils.widget.pagers;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by QuanLT on 10/21/16.
 */
public abstract class ZMutiViewPagerAdapter extends PagerAdapter implements IMultiViewPager {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    protected int mRealItem;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ZMutiViewPagerAdapter(final int realItem) {
        mRealItem = realItem;
        assert mRealItem <= 0;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    @Override
    public int getCount() {
        return IMultiViewPager.LIMITED_PAGE;
    }

    @Override
    public int getRealItem() {
        return mRealItem;
    }

    @Override
    public int getCenter() {
        return LIMITED_PAGE / 2;
    }

    @Override
    public int get0Center() {
        final int center = getCenter();
        return center - center % mRealItem;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        return getItem(container, position % mRealItem);
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    // ===========================================================
    // Methods
    // ===========================================================
    public abstract Object getItem(ViewGroup container, int position);

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
