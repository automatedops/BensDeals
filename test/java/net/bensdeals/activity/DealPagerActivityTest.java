package net.bensdeals.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowAlertDialog;
import com.xtremelabs.robolectric.shadows.ShadowDialog;
import com.xtremelabs.robolectric.tester.android.view.TestMenuItem;
import net.bensdeals.R;
import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import net.bensdeals.utils.TestImageLoader;
import net.bensdeals.utils.TestRemoteTask;
import net.bensdeals.views.IndicatorView;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import roboguice.inject.InjectView;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class DealPagerActivityTest {
    @Inject DealPagerActivity activity;
    @Inject TestImageLoader imageLoader;
    @Inject TestRemoteTask remoteTask;
    @InjectView(R.id.deals_view_pager) ViewPager viewPager;
    @InjectView(R.id.indicator) IndicatorView indicatorView;

    @Test
    public void onCreate_shouldSetAdapter() throws Exception {
        activity.onCreate(null);
        PagerAdapter adapter = viewPager.getAdapter();
        expect(adapter).not.toBeNull();
        expect(adapter).toBeInstanceOf(PagerAdapter.class);
    }

    @Test
    @Ignore
    public void onCreate_shouldCreateLoadingDialog() throws Exception {
        Robolectric.getUiThreadScheduler().pause();
        activity.onCreate(null);
        Dialog latestDialog = ShadowDialog.getLatestDialog();
        expect(latestDialog).not.toBeNull();
        Robolectric.getUiThreadScheduler().advanceToLastPostedRunnable();
        expect(latestDialog.isShowing()).toBeTrue();
    }

    @Test
    public void shouldCancelPendingImageLoadingOnExit() throws Exception {
        activity.onCreate(null);
        expect(imageLoader.cancelOutstandingRequestsWasCalled()).toBeFalse();
        activity.onPause();
        expect(imageLoader.cancelOutstandingRequestsWasCalled()).toBeTrue();
    }

    @Test
    public void tappingRefreshMenu_shouldFetchDeals() throws Exception {
        activity.onMenuItemSelected(0, new TestMenuItem(R.id.item_refresh));
        expect(remoteTask.getLatestRequestPath()).toEqual("http://bensbargains.net/rss/");
    }

    @Test
    public void tappingPagesMenu_shouldShowDialog() throws Exception {
        activity.onMenuItemSelected(0, new TestMenuItem(R.id.item_pages));
        AlertDialog latestAlertDialog = ShadowAlertDialog.getLatestAlertDialog();
        expect(latestAlertDialog).not.toBeNull();
    }

    @Test
    public void onConfigurationChanged_shouldSetIndexToFirst() throws Exception {
        activity.onCreate(null);
        indicatorView.setSelected(2);
        Configuration configuration = new Configuration();
        configuration.orientation = Configuration.ORIENTATION_PORTRAIT;
        activity.onConfigurationChanged(configuration);
        expect(indicatorView.selected).toEqual(0);
    }

    @Test
    public void onConfigurationChanged_shouldResetAdapter() throws Exception {
        activity.onCreate(null);
        viewPager.setAdapter(null);
        Configuration configuration = new Configuration();
        configuration.orientation = Configuration.ORIENTATION_PORTRAIT;
        activity.onConfigurationChanged(configuration);
        expect(viewPager.getAdapter()).not.toBeNull();
    }

    @Test
    public void onCreate_shouldPageChangeListener() throws Exception {
        activity.onCreate(null);
        expect(shadowOf(viewPager).getOnPageChangeListener()).not.toBeNull();
    }

    @Test
    public void onLoadFailure_shouldShowAlertDialog() throws Exception {
        activity.onCreate(null);
        remoteTask.simulateFailedResponse();
        ShadowAlertDialog dialog = shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        expect(dialog.isShowing()).toBeTrue();
        expect(dialog.getMessage()).toEqual("Failed to load Homepage…");
    }
}
