package net.bensdeals.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.inject.Inject;
import com.google.inject.ProvidedBy;
import com.google.inject.Provider;
import net.bensdeals.R;
import net.bensdeals.listener.OnSearchListener;
import net.bensdeals.utils.LayoutInflaterWithInjection;
import roboguice.util.Strings;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;
import static android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT;

@ProvidedBy(SearchEditView.ViewProvider.class)
public class SearchEditView extends RelativeLayout implements View.OnClickListener, TextView.OnEditorActionListener {
    public View searchButton;
    public EditText searchEditText;
    private OnSearchListener searchListener;
    private float STROKE_WIDTH;
    private final Paint paint;
    public TextView searchText;

    public SearchEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(ANTI_ALIAS_FLAG);
        STROKE_WIDTH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3.3f, getResources().getDisplayMetrics());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        searchButton = findViewById(R.id.search_button);
        searchEditText = (EditText) findViewById(R.id.search_edittext);
        searchEditText.setOnEditorActionListener(this);
        searchText = (TextView) findViewById(R.id.search_text);
        searchText.setOnClickListener(this);
        searchButton.setOnClickListener(this);
    }

    public void setEditText(String prefix) {
        if (Strings.isEmpty(prefix)) return;
        searchEditText.setText(prefix);
    }

    public void setOnSearchListener(OnSearchListener searchListener) {
        this.searchListener = searchListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(getResources().getColor(R.color.light_navy_blue));
        canvas.drawLine(getLeft() + getPaddingLeft(), getBottom(), getRight(), getBottom(), paint);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_text:
                showEditText();
                break;
            case R.id.search_button:
                doSearch();
        }
    }

    private void doSearch() {
        if (searchListener != null) {
            searchListener.onSearch(searchEditText.getText().toString());
            searchText.setText(searchEditText.getText().toString());
            searchText.setVisibility(VISIBLE);
            ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(searchEditText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            searchEditText.setVisibility(GONE);
        }
    }

    private void showEditText() {
        searchText.setVisibility(GONE);
        searchEditText.setVisibility(VISIBLE);
        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, SHOW_IMPLICIT);
        searchEditText.requestFocus();
    }

    @Override
    public boolean onEditorAction(TextView textView, int imeAction, KeyEvent keyEvent) {
        if(imeAction == IME_ACTION_SEARCH) {
            doSearch();
            return true;
        }
        return false;
    }

    public static class ViewProvider implements Provider<SearchEditView> {
        LayoutInflaterWithInjection<SearchEditView> inflater;

        @Inject
        public ViewProvider(LayoutInflaterWithInjection<SearchEditView> inflater) {
            this.inflater = inflater;
        }

        @Override
        public SearchEditView get() {
            return inflater.inflate(R.layout.search_edit_layout);
        }
    }
}
