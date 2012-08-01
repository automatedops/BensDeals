package net.bensdeals.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.utils.LayoutInflaterWithInjection;
import net.bensdeals.views.DealItemView;

import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static net.bensdeals.adapter.DealsAdapter.Orientation.LANDSCAPE;
import static net.bensdeals.adapter.DealsAdapter.Orientation.PORTRAIT;

public class DealsAdapter extends PagerAdapter {
    private Orientation orientation;

    public enum Orientation {
        LANDSCAPE {
            @Override
            public int getLayout() {
                return R.layout.deal_item_layout_landscape;
            }
        },
        PORTRAIT {
            @Override
            public int getLayout() {
                return R.layout.deal_item_layout_portrait;
            }
        };

        public abstract int getLayout();

    }
    List<Deal> items = Lists.newArrayList();
    private LayoutInflaterWithInjection<DealItemView> inflater;

    @Inject
    public DealsAdapter(LayoutInflaterWithInjection<DealItemView> inflater, Context context) {
        this.inflater = inflater;
        setOrientation(context.getResources().getConfiguration());
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
        DealItemView dealItemView = inflater.inflate(orientation.getLayout());
        container.addView(dealItemView, 0);
        return dealItemView.render(items.get(position));
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
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

    public void setOrientation(Configuration configuration) {
        this.orientation = ORIENTATION_LANDSCAPE == configuration.orientation ? LANDSCAPE : PORTRAIT;
    }

    public Deal getItem(int currentItem) {
        return items.get(currentItem);
    }
}
