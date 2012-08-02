package net.bensdeals.adapter;

import android.view.View;
import android.view.ViewGroup;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.model.wrapper.SearchResponseWrapper;
import net.bensdeals.utils.LayoutInflaterWithInjection;
import net.bensdeals.views.SearchItemView;

import java.util.Collections;
import java.util.List;

public class SearchAdapter extends BaseListAdapter<SearchResponseWrapper.SearchItemWrapper> {
    LayoutInflaterWithInjection<View> inflater;

    @Inject
    public SearchAdapter(LayoutInflaterWithInjection<View> inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SearchResponseWrapper.SearchItemWrapper searchItemWrapper = items.get(i);
        if(view == null)
            view = inflater.inflate(R.layout.search_item_layout);
        return ((SearchItemView) view).renderSearchItem(searchItemWrapper);
    }

    @Override
    public void replaceAll(List<SearchResponseWrapper.SearchItemWrapper> items) {
        Collections.sort(items);
        super.replaceAll(items);
    }
}
