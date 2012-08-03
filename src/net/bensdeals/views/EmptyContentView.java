package net.bensdeals.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.google.inject.Inject;
import com.google.inject.ProvidedBy;
import com.google.inject.Provider;
import net.bensdeals.R;
import net.bensdeals.utils.LayoutInflaterWithInjection;

@ProvidedBy(EmptyContentView.ViewProvider.class)
public class EmptyContentView extends LinearLayout {

    public View emptyContentText;

    public EmptyContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        emptyContentText = findViewById(R.id.empty_content_text);
    }

    public void show() {
        emptyContentText.setVisibility(VISIBLE);
    }

    public void hide() {
        emptyContentText.setVisibility(GONE);
    }

    public static class ViewProvider implements Provider<EmptyContentView> {
        LayoutInflaterWithInjection<EmptyContentView> inflater;

        @Inject
        public ViewProvider(LayoutInflaterWithInjection<EmptyContentView> inflater) {
            this.inflater = inflater;
        }

        @Override
        public EmptyContentView get() {
            return inflater.inflate(R.layout.empty_content_layout);
        }
    }
}
