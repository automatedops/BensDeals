package net.bensdeals.views;

import android.view.MotionEvent;
import android.view.View;

import com.xtremelabs.robolectric.shadows.ShadowView;
import net.bensdeals.R;
import net.bensdeals.listener.OnIndexChangeListener;
import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class IndicatorViewTest {
    @Inject LayoutInflaterWithInjection<View> inflater;
    public IndicatorView indicatorView;
    public TestIndexChangeListener testIndexChangeListener;

    @Before
    public void setup() throws Exception {
        indicatorView = (IndicatorView) inflater.inflate(R.layout.deal_pager_layout).findViewById(R.id.indicator);
        testIndexChangeListener = new TestIndexChangeListener();
        indicatorView.setIndexChangeListener(testIndexChangeListener);
    }

    @Test
    public void shouldHaveTouchListener() throws Exception {
        expect(shadowOf(indicatorView).getOnTouchListener()).not.toBeNull();
    }

    @Test
    public void shouldHaveIndexChangedWhenTouchIndicatorViewOnTheRightOfTheView() throws Exception {
        indicatorView.setSelected(0);
        indicatorView.layout(0, 0, 20, 1);
        indicatorView.dispatchTouchEvent(MotionEvent.obtain(1l, 1l, MotionEvent.ACTION_UP, 1, 1, -1));
        expect(testIndexChangeListener.changedIndex).toEqual(1);
    }

    @Test
    public void shouldHaveIndexChangedWhenTouchIndicatorViewOnTheLeftOfTheView() throws Exception {
        indicatorView.setSelected(2);
        indicatorView.layout(0, 0, 20, 1);
        indicatorView.dispatchTouchEvent(MotionEvent.obtain(1l, 1l, MotionEvent.ACTION_UP, 0, 0, -1));
        expect(testIndexChangeListener.changedIndex).toEqual(1);
    }

    @Test
    public void setSelected_shouldPostInvalidate() throws Exception {
        ShadowView shadowView = shadowOf(indicatorView);
        expect(shadowView.postInvalidateWasCalled()).toBeFalse();
        indicatorView.setSelected(19);
        expect(indicatorView.selected).toEqual(19);
        expect(shadowView.postInvalidateWasCalled()).toBeTrue();
    }

    public static class TestIndexChangeListener implements OnIndexChangeListener {
        public int changedIndex;

        @Override
        public void indexChanged(int index) {
            this.changedIndex = index;
        }
    }
}
