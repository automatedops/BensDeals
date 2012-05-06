package net.bensdeals.adapter;

import android.widget.BaseAdapter;
import com.google.inject.internal.Lists;

import java.util.List;

public abstract class BaseDealAdapter<T> extends BaseAdapter {
    List<T> items = Lists.newArrayList();

    @Override public int getCount() { return items.size(); }
    @Override public T getItem(int i) { return items.get(i); }
    @Override public long getItemId(int i) { return i; }

    public void replaceAll(List<T> newItems){
        this.items.clear();
        addAll(newItems);
    }

    public void addAll(List<T> newItems) {
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }
}
