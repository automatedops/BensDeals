package net.bensdeals.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import net.bensdeals.provider.XMLPathProvider;

public class PageSpinnerAdapter implements SpinnerAdapter {
    private XMLPathProvider.XMLPath[] mValues;

    public PageSpinnerAdapter(XMLPathProvider.XMLPath[] values) {
        mValues = values;
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return mValues.length;
    }

    @Override
    public Object getItem(int i) {
        return mValues[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return mValues.length == 0;
    }
}
