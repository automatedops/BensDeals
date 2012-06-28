package net.bensdeals.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.utils.LayoutInflaterWithInjection;
import net.bensdeals.views.DealItemView;
import net.bensdeals.views.IndicatorView;

import java.util.List;

public class DealsAdapter extends PagerAdapter {
    List<Deal> items = Lists.newArrayList();

    private LayoutInflaterWithInjection<DealItemView> inflater;
    private IndicatorView indicatorView;

    @Inject
    public DealsAdapter(LayoutInflaterWithInjection<DealItemView> inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void replaceAll(List<Deal> newItems) {
        this.items.clear();
        addAll(newItems);
    }

    public void addAll(List<Deal> newItems) {
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        DealItemView dealItemView = inflater.inflate(R.layout.deal_gallery_item);
        container.addView(dealItemView, 0);
        return dealItemView.render(items.get(position));
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        indicatorView.setSelected(position + 1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public PagerAdapter setOnIndexChangedListener(IndicatorView indicatorView) {
        this.indicatorView = indicatorView;
        return this;
    }
}
