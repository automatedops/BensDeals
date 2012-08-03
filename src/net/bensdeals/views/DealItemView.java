package net.bensdeals.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.network.ImageLoader;
import net.bensdeals.provider.CurrentDateProvider;
import net.bensdeals.utils.DateFormatter;
import net.bensdeals.utils.IntentUtil;

import static android.text.Html.fromHtml;

public class DealItemView extends LinearLayout {
    @Inject ImageLoader imageLoader;
    @Inject CurrentDateProvider dateProvider;
    private TextView titleText;
    private ImageView imageView;
    private TextView descText;
    public View shareButton;
    public TextView timeText;
    public View container;

    public DealItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleText = (TextView) findViewById(R.id.title_text);
        imageView = (ImageView) findViewById(R.id.gallery_image);
        descText = (TextView) findViewById(R.id.desc_text);
        shareButton = findViewById(R.id.share_button);
        timeText = (TextView) findViewById(R.id.deal_time_text);
        container = findViewById(R.id.gallery_text_container);
    }

    public DealItemView render(final Deal deal) {
        titleText.setText(fromHtml(deal.getTitle()));
        descText.setText(fromHtml(deal.getDescription()));
        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.intentForWeb(getContext(), deal.getLink());
            }
        });
        imageLoader.loadImage(imageView, deal.getImageUrl());
        shareButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.intentForShare(getContext(), deal);
            }
        });
        timeText.setText(DateFormatter.formatDate(dateProvider, deal.getDate(), getContext()));
        return this;
    }
}
