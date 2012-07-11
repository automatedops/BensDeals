package net.bensdeals.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.bensdeals.R;
import net.bensdeals.provider.XMLPathProvider;

public class ComboBox extends LinearLayout {
    public TextView titleText;

    public ComboBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleText = (TextView) findViewById(R.id.page_title_view);
    }

    public void render(XMLPathProvider.XMLPath xmlPath) {
        titleText.setText(xmlPath.getTitle());
    }
}
