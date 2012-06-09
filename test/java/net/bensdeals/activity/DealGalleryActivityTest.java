package net.bensdeals.activity;

import android.app.Dialog;
import android.widget.BaseAdapter;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.shadows.ShadowDialog;
import net.bensdeals.R;
import net.bensdeals.adapter.GalleryAdapter;
import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import net.bensdeals.util.TestRemoteTask;
import net.bensdeals.views.IndicatorView;
import net.bensdeals.views.gallery.GalleryView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import roboguice.inject.InjectView;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class DealGalleryActivityTest {
    @Inject DealGalleryActivity activity;
    @InjectView(R.id.deal_gallery) GalleryView galleryView;
    @InjectView(R.id.indicator) IndicatorView indicatorView;
    @Inject TestRemoteTask remoteTask;

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void onCreate_shouldSetAdapter() throws Exception {
        activity.onCreate(null);
        BaseAdapter adapter = galleryView.getAdapter();
        expect(adapter).not.toBeNull();
        expect(adapter).toBeInstanceOf(GalleryAdapter.class);
    }

    @Test
    public void onCreate_shouldMakeApiRequest() throws Exception {
        activity.onCreate(null);
        expect(remoteTask.hasPendingRequests()).toBeTrue();
        expect(remoteTask.getLatestRequest().getUrlString()).toEqual("http://bensbargains.net/rss/");
    }

    @Test
    public void onCreate_shouldCreateLoadingDialog() throws Exception {
        activity.onCreate(null);
        Dialog latestDialog = ShadowDialog.getLatestDialog();
        expect(latestDialog).not.toBeNull();
        expect(latestDialog.isShowing()).toBeTrue();
    }

    @Test
    public void shouldDismissAfterSuccessResponse() throws Exception {
        activity.onCreate(null);
        remoteTask.simulateResponse("homepage.xml");
        expect(ShadowDialog.getLatestDialog().isShowing()).toBeFalse();
    }

    @Test
    public void shouldDismissAfterFailedResponse() throws Exception {
        activity.onCreate(null);
        remoteTask.simulateResponse(null);
        expect(ShadowDialog.getLatestDialog().isShowing()).toBeFalse();
    }

    @Test
    public void onCreate_shouldSetDataToAdapterIfGotSuccessResponse() throws Exception {
        activity.onCreate(null);
        remoteTask.simulateResponse("homepage.xml");
        expect(galleryView.getAdapter().getCount()).toEqual(20);
    }

    @Test
    public void responseSuccess_shouldSetIndexViewIndexToFirstPosition() throws Exception {
        activity.onCreate(null);
        remoteTask.simulateResponse("homepage.xml");
        expect(indicatorView.selected).toEqual(1);
        expect(shadowOf(indicatorView).postInvalidateWasCalled()).toBeTrue();
    }
}
