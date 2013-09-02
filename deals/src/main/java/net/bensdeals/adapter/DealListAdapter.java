package net.bensdeals.adapter;

import javax.inject.Inject;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.common.collect.Lists;
import net.bensdeals.R;
import net.bensdeals.activity.DealApplication;
import net.bensdeals.model.Deal;
import net.bensdeals.provider.CurrentDateProvider;
import net.bensdeals.views.DealItemView;

public class DealListAdapter extends BaseAdapter {
    @Inject CurrentDateProvider dateProvider;
    private List<Deal> mItems = Lists.newArrayList();
    private final LayoutInflater mLayoutInflater;

    public DealListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        ((DealApplication) DealApplication.getContext()).inject(this);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Deal getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.deal_item, null, false);
        }
        return ((DealItemView) view).render(mItems.get(i), dateProvider);
    }

    public void replaceAll(List<Deal> list) {
        if (list != null) {
            mItems.clear();
            mItems.addAll(list);
            notifyDataSetChanged();
        }
    }
}
