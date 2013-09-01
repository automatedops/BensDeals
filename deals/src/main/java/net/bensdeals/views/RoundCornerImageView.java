package net.bensdeals.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;
import net.bensdeals.R;

public class RoundCornerImageView extends ImageView {
    public static final int STROKE_WIDTH = 5;
    public static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        RectF outerRect = new RectF(
                STROKE_WIDTH / 2,
                STROKE_WIDTH / 2,
                getWidth() - STROKE_WIDTH / 2,
                getHeight() - STROKE_WIDTH / 2
        );
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(outerRect, STROKE_WIDTH, STROKE_WIDTH, paint);
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(getResources().getColor(R.color.light_navy_blue));
        canvas.drawRoundRect(outerRect, STROKE_WIDTH, STROKE_WIDTH, paint);
    }
}
