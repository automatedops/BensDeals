package net.bensdeals.views;

import android.widget.ImageView;
import android.widget.TextView;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import net.bensdeals.util.TestImageLoader;
import net.bensdeals.utils.LayoutInflaterWithInjection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class DealItemViewTest {
    @Inject LayoutInflaterWithInjection<DealItemView> inflater;
    @Inject TestImageLoader imageLoader;
    public DealItemView dealItemView;
    public Deal deal;
    private TextView titleText;
    private ImageView imageView;
    private TextView descText;

    @Before
    public void setup() throws Exception {
        dealItemView = inflater.inflate(R.layout.deal_item_layout);
        deal = new Deal().setDescription("deal desc").setLink("some/web/link").setTitle("deal title").setImageUrl("some/deal/image");
        titleText = (TextView) dealItemView.findViewById(R.id.title_text);
        imageView = (ImageView) dealItemView.findViewById(R.id.gallery_image);
        descText = (TextView) dealItemView.findViewById(R.id.desc_text);
    }

    @Test
    public void testRender() throws Exception {
        dealItemView.render(deal);
        expect(titleText).toHaveText("deal title");
        expect(descText).toHaveText("deal desc");
        expect(imageLoader.loadedImageUrl(imageView)).toEqual("some/deal/image");
    }
}
