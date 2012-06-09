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
public class DealGalleryItemTest {
    @Inject LayoutInflaterWithInjection<DealGalleryItem> inflater;
    @Inject TestImageLoader imageLoader;
    public DealGalleryItem galleryItem;
    public Deal deal;
    private TextView titleText;
    private ImageView imageView;
    private TextView descText;

    @Before
    public void setup() throws Exception {
        galleryItem = inflater.inflate(R.layout.deal_gallery_item);
        deal = new Deal().setDescription("deal desc").setLink("some/web/link").setTitle("deal title").setImageUrl("some/deal/image");
        titleText = (TextView) galleryItem.findViewById(R.id.title_text);
        imageView = (ImageView) galleryItem.findViewById(R.id.gallery_image);
        descText = (TextView) galleryItem.findViewById(R.id.desc_text);
    }

    @Test
    public void testRender() throws Exception {
        galleryItem.render(deal);
        expect(titleText).toHaveText("deal title");
        expect(descText).toHaveText("deal desc");
        expect(imageLoader.loadedImageUrl(imageView)).toEqual("some/deal/image");
    }
}
