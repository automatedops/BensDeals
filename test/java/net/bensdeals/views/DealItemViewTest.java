package net.bensdeals.views;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import net.bensdeals.support.TestCase;
import net.bensdeals.util.TestImageLoader;
import net.bensdeals.utils.LayoutInflaterWithInjection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class DealItemViewTest extends TestCase {
    @Inject LayoutInflaterWithInjection<DealItemView> inflater;
    @Inject TestImageLoader imageLoader;
    public DealItemView dealItemView;
    public Deal deal;
    private TextView titleText;
    private ImageView imageView;
    private TextView descText;
    private View shareButton;

    @Before
    public void setup() throws Exception {
        dealItemView = inflater.inflate(R.layout.deal_item_layout);
        deal = new Deal().setDescription("deal desc").setLink("some/web/link").setTitle("deal title").setImageUrl("some/deal/image");
        titleText = (TextView) findViewById(R.id.title_text);
        imageView = (ImageView) findViewById(R.id.gallery_image);
        descText = (TextView) findViewById(R.id.desc_text);
        shareButton = findViewById(R.id.share_button);
    }

    @Override
    protected View findViewById(int viewId) {
        return dealItemView.findViewById(viewId);
    }

    @Test
    public void testRender() throws Exception {
        dealItemView.render(deal);
        expect(titleText).toHaveText("deal title");
        expect(descText).toHaveText("deal desc");
        expect(imageLoader.loadedImageUrl(imageView)).toEqual("some/deal/image");
    }

    @Test
    public void shouldHaveShareClickListener() throws Exception {
        dealItemView.render(deal);
        expect(shadowOf(shareButton).getOnClickListener()).not.toBeNull();
        expect(shadowOf(descText).getOnClickListener()).not.toBeNull();
    }

    @Test
    public void shouldLaunchShareIntent() throws Exception {
        dealItemView.render(deal);
        shareButton.performClick();
        Intent intent = shadowOf(new Activity()).peekNextStartedActivity();
        expect(intent.getAction()).toEqual(Intent.ACTION_CHOOSER);
        Intent parcelableExtra = (Intent) intent.getParcelableExtra(Intent.EXTRA_INTENT);
        expect(parcelableExtra.getStringExtra(Intent.EXTRA_SUBJECT)).toEqual("deal title");
        expect(parcelableExtra.getCharSequenceExtra(Intent.EXTRA_TEXT)).toEqual("deal desc");
    }
}
