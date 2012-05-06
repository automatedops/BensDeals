package net.bensdeals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.network.ImageLoader;
import net.bensdeals.views.DealGalleryItem;

public class GalleryAdapter extends BaseDealAdapter<Deal> {
    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    @Inject
    public GalleryAdapter(Context context, ImageLoader imageLoader) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageLoader = imageLoader;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DealGalleryItem itemView = (DealGalleryItem) (view == null ? inflater.inflate(R.layout.deal_gallery_item, null, false) : view);
        return itemView.render(getItem(i), imageLoader);
    }
}
