package net.bensdeals.listener;

import android.support.v4.view.ViewPager;
import com.google.inject.Inject;
import net.bensdeals.views.IndicatorView;

public class OnPageChangeListener implements ViewPager.OnPageChangeListener {
    private IndicatorView indicatorView;

    @Inject
    public OnPageChangeListener() { }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        if (indicatorView != null) indicatorView.setSelected(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    public OnPageChangeListener setIndicatorView(IndicatorView indicatorView) {
        this.indicatorView = indicatorView;
        return this;
    }
}
