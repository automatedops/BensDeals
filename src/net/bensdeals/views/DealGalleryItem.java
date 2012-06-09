package net.bensdeals.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.network.ImageLoader;

import static android.text.Html.fromHtml;

public class DealGalleryItem extends LinearLayout {
    @Inject ImageLoader imageLoader;
    private TextView titleText;
    private ImageView imageView;
    private TextView descText;

    public DealGalleryItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleText = (TextView) findViewById(R.id.title_text);
        imageView = (ImageView) findViewById(R.id.gallery_image);
        descText = (TextView)findViewById(R.id.desc_text);
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        imageView.getLayoutParams().height = (int) (heightPixels * 0.45);
    }

    public DealGalleryItem render(Deal deal) {
        imageView.setImageDrawable(null);
        titleText.setText(fromHtml(deal.getTitle()));
        descText.setText(fromHtml(deal.getDescription()));
        imageLoader.loadImage(imageView, deal.getImageUrl());
        return this;
    }
}
