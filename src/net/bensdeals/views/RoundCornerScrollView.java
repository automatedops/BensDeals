package net.bensdeals.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import net.bensdeals.R;

public class RoundCornerScrollView extends LinearLayout {
    public static final int STROKE_WIDTH = 5;
    public static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RoundCornerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Resources resources = getResources();
        RectF outerRect = new RectF(
                STROKE_WIDTH / 2,
                STROKE_WIDTH / 2,
                getWidth() - STROKE_WIDTH / 2,
                getHeight() - STROKE_WIDTH / 2
        );
        paint.setColor(resources.getColor(R.color.dark_navy_blue));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(outerRect, STROKE_WIDTH, STROKE_WIDTH, paint);
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(resources.getColor(R.color.light_navy_blue));
        canvas.drawRoundRect(outerRect, STROKE_WIDTH, STROKE_WIDTH, paint);
    }
}
