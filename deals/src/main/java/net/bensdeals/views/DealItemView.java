package net.bensdeals.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.provider.CurrentDateProvider;
import net.bensdeals.utils.DateFormatter;

import static android.text.Html.fromHtml;

public class DealItemView extends LinearLayout {
    private TextView titleText;
    private ImageView imageView;
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
        timeText = (TextView) findViewById(R.id.deal_time_text);
    }

    public DealItemView render(final Deal deal, CurrentDateProvider dateProvider) {
        titleText.setText(fromHtml(deal.getTitle()));
        timeText.setText(DateFormatter.formatDate(dateProvider, deal.getDate(), getContext()));

        imageView.setImageBitmap(null);
        Picasso.with(imageView.getContext()).load(deal.getImageUrl()).into(imageView);
        return this;
    }
}
