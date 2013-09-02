package net.bensdeals.views;

import javax.inject.Inject;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import net.bensdeals.support.TestCase;
import net.bensdeals.utils.TestCurrentDateProvider;
import net.bensdeals.utils.TestImageLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class DealItemViewTest extends TestCase {
    @Inject LayoutInflaterWithInjection<DealItemView> inflater;
    @Inject TestCurrentDateProvider dateProvider;
    @Inject TestImageLoader imageLoader;
    public DealItemView dealItemView;
    public Deal deal;
    private TextView titleText;
    private ImageView imageView;
    private TextView descText;
    private View shareButton;
    public TextView timeText;
    public View container;

    @Before
    public void setup() throws Exception {
        dealItemView = inflater.inflate(R.layout.deal_item);
        deal = new Deal().setDescription("deal desc").setLink("some/web/link").setTitle("deal title").setImageUrl("some/deal/image").setDate(new Date());
        titleText = (TextView) findViewById(R.id.title_text);
        imageView = (ImageView) findViewById(R.id.gallery_image);
        descText = (TextView) findViewById(R.id.desc_text);
        container = findViewById(R.id.gallery_text_container);
        timeText = (TextView) findViewById(R.id.deal_time_text);
        shareButton = findViewById(R.id.share_button);
    }

    @Override
    protected View findViewById(int viewId) {
        return dealItemView.findViewById(viewId);
    }

    @Test
    public void testRender() throws Exception {
        Date now = dateProvider.get();
        Date date = new Date(now.getTime() - 70000l);
        dealItemView.render(deal.setDate(date), dateProvider);
        expect(titleText).toHaveText("deal title");
        expect(descText).toHaveText("deal desc");
        expect(imageLoader.loadedImageUrl(imageView)).toEqual("some/deal/image");
        expect(timeText).toHaveText("1 minute ago");
    }

    @Test
    public void shouldHaveShareClickListener() throws Exception {
        dealItemView.render(deal, dateProvider);
        expect(shadowOf(shareButton).getOnClickListener()).not.toBeNull();
        expect(shadowOf(container).getOnClickListener()).not.toBeNull();
    }

    @Test
    public void shouldLaunchShareIntent() throws Exception {
        dealItemView.render(deal, dateProvider);
        shareButton.performClick();
        Intent intent = shadowOf(new Activity()).peekNextStartedActivity();
        expect(intent.getAction()).toEqual(Intent.ACTION_CHOOSER);
        Intent parcelableExtra = (Intent) intent.getParcelableExtra(Intent.EXTRA_INTENT);
        expect(parcelableExtra.getStringExtra(Intent.EXTRA_SUBJECT)).toEqual("deal title");
        expect(parcelableExtra.getCharSequenceExtra(Intent.EXTRA_TEXT)).toEqual("deal desc");
    }
}
