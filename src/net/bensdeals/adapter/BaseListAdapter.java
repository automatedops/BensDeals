package net.bensdeals.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.inject.internal.Lists;

import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected List<T> items = Lists.newArrayList();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void replaceAll(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public abstract View getView(int i, View view, ViewGroup viewGroup);
}
