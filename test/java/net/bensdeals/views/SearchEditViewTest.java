package net.bensdeals.views;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.listener.OnSearchListener;
import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;
import static com.pivotallabs.robolectricgem.expect.Expect.expect;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class SearchEditViewTest {
    public final TestSearchListener searchListener = new TestSearchListener();
    public final String prefix = "this is a search key";
    @Inject SearchEditView searchEditView;
    @Inject Context context;
    public EditText editText;
    private View searchButton;
    private View textView;

    @Before
    public void setup() throws Exception {
        editText = (EditText) findViewById(R.id.search_edittext);
        searchButton = findViewById(R.id.search_button);
        textView = findViewById(R.id.search_text);
        searchEditView.setOnSearchListener(searchListener);
        searchEditView.setEditText(prefix);
    }

    @Test
    public void shouldHandleIMESearchAction() throws Exception {
        expect(searchListener.onSearchWasCalled).toBeFalse();
        shadowOf(editText).triggerEditorAction(IME_ACTION_SEARCH);
        expect(searchListener.onSearchWasCalled).toBeTrue();
    }

    @Test
    public void shouldHideTextAndShowEditText() throws Exception {
        searchButton.performClick();
        expect(editText).toBeGone();
        expect(textView).toBeVisible();
    }

    @Test
    public void shouldShowTextAndHideEditText() throws Exception {
        searchButton.performClick();
        textView.performClick();
        expect(editText).toBeVisible();
        expect(textView).toBeInvisible();
    }

    private View findViewById(int viewId) {
        return searchEditView.findViewById(viewId);
    }

    public class TestSearchListener implements OnSearchListener {
        public boolean onSearchWasCalled;
        public String searchText;

        @Override
        public void onSearch(String searchText) {
            this.searchText = searchText;
            this.onSearchWasCalled = true;
        }
    }
}
