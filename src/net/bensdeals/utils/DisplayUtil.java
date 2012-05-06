package net.bensdeals.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.google.inject.Inject;

public class DisplayUtil {
    private final DisplayMetrics metrics;
    @Inject
    public DisplayUtil(Context context) {
        this.metrics = context.getResources().getDisplayMetrics();
    }

    public int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}
