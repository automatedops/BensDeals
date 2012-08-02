package net.bensdeals.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.model.SearchItem;
import net.bensdeals.model.wrapper.SearchResponseWrapper;
import net.bensdeals.network.ImageLoader;

import java.util.List;

public class SearchItemView extends RelativeLayout {
    @Inject ImageLoader imageLoader;
    private ImageView itemImage;
    private TextView itemDesc;
    private TextView itemTitle;

    public SearchItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        itemImage = (ImageView) findViewById(R.id.search_item_image);
        itemTitle = (TextView) findViewById(R.id.search_item_title);
        itemDesc = (TextView) findViewById(R.id.search_item_desc);
    }

    public SearchItemView renderSearchItem(SearchResponseWrapper.SearchItemWrapper searchItemWrapper) {
        SearchItem item = searchItemWrapper.item;
        List<SearchItem.Image> images = item.getImages();
        if (!images.isEmpty()) {
            SearchItem.Image image = images.get(0);
            imageLoader.loadImage(itemImage, image.getLink());
        }
        itemTitle.setText(item.getTitle());
        itemDesc.setText(item.getDescription());
        return this;
    }
}
