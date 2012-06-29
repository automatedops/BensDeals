package net.bensdeals.views;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.network.ImageLoader;

import static android.text.Html.fromHtml;

public class DealItemView extends LinearLayout {
    @Inject ImageLoader imageLoader;
    private TextView titleText;
    private ImageView imageView;
    private TextView descText;
    public View shareButton;

    public DealItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleText = (TextView) findViewById(R.id.title_text);
        imageView = (ImageView) findViewById(R.id.gallery_image);
        descText = (TextView)findViewById(R.id.desc_text);
        Linkify.addLinks(descText, Linkify.WEB_URLS);
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        imageView.getLayoutParams().height = (int) (heightPixels * 0.45);
        shareButton = findViewById(R.id.share_button);
    }

    public DealItemView render(final Deal deal) {
        titleText.setText(fromHtml(deal.getTitle()));
        descText.setText(fromHtml(deal.getDescription()));
        imageLoader.loadImage(imageView, deal.getImageUrl());
        shareButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_SUBJECT, deal.getTitle());
                intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(deal.getDescription()));
                getContext().startActivity(Intent.createChooser(intent, "Share via"));
            }
        });
        return this;
    }
}
