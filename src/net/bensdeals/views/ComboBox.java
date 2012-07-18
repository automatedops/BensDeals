package net.bensdeals.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.bensdeals.R;
import net.bensdeals.provider.XMLPathProvider;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class ComboBox extends LinearLayout {
    public TextView titleText;
    public final Paint paint;
    public float WIDTH = 17;
    public float STROKE_WIDTH = 5;


    public ComboBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(ANTI_ALIAS_FLAG);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        WIDTH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 11.3f, displayMetrics);
        STROKE_WIDTH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3.3f, displayMetrics);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleText = (TextView) findViewById(R.id.page_title_view);
    }

    public void render(XMLPathProvider.XMLPath xmlPath) {
        titleText.setText(xmlPath.getTitle());
        setVisibility(VISIBLE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(getResources().getColor(R.color.light_navy_blue));
        canvas.drawLine(getLeft() + getPaddingLeft(), getBottom(), getRight(), getBottom(), paint);

        paint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.moveTo(getRight() - WIDTH, getBottom());
        path.lineTo(getRight(), getBottom());
        path.lineTo(getRight(), getBottom() - WIDTH);
        path.lineTo(getRight() - WIDTH, getBottom());
        path.close();
        canvas.drawPath(path, paint);
    }
}
