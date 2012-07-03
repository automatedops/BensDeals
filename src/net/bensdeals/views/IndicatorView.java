package net.bensdeals.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import net.bensdeals.R;

public class IndicatorView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public int selected = 0;
    private static final int TOTAL_COUNT = 20 + 1;
    public final int light_navy;

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        light_navy = getResources().getColor(R.color.light_navy_blue);
    }

    public void setSelected(int selected) {
        if(this.selected == selected) return;
        this.selected = selected;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth() / TOTAL_COUNT;
        for (int i = 1; i < TOTAL_COUNT; i++) {
            boolean selected = i == this.selected;
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(75);
            paint.setColor(selected ? light_navy : Color.WHITE);
            canvas.drawCircle(width * i + width/2, width, width/3, paint);

            if (selected) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(2);
                paint.setAlpha(0);
                paint.setColor(Color.WHITE);
                canvas.drawCircle(width * i + width/2, width, width/3, paint);
            }
        }
    }
}
