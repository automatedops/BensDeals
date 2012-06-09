package net.bensdeals.adapter;

import android.view.View;
import android.view.ViewGroup;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.utils.LayoutInflaterWithInjection;
import net.bensdeals.views.DealGalleryItem;

public class GalleryAdapter extends BaseDealAdapter<Deal> {
    private LayoutInflaterWithInjection<DealGalleryItem> inflater;

    @Inject
    public GalleryAdapter(LayoutInflaterWithInjection<DealGalleryItem> inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DealGalleryItem itemView = (DealGalleryItem) (view == null ? inflater.inflate(R.layout.deal_gallery_item) : view);
        return itemView.render(getItem(i));
    }
}
