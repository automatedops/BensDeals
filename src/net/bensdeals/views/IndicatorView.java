package net.bensdeals.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class IndicatorView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int selected = 0;
    private static final int TOTAL_COUNT = 20 + 1;

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSelected(int selected) {
        this.selected = selected;
        postInvalidate();
    }

    public int getSelected() {
        return selected;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth() / TOTAL_COUNT;
        for (int i = 1; i < TOTAL_COUNT; i++) {
            paint.setColor(i == selected ? Color.WHITE : Color.GRAY);
            canvas.drawCircle(width * i + width/2, width, width/3, paint);
        }
    }
}
