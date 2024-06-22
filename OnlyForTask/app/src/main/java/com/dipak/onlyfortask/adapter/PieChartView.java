package com.dipak.onlyfortask.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class PieChartView extends View {

    private Paint paint;
    private float[] data; // Array of values for the sectors
    private int[] colors; // Array of colors for the sectors

    public PieChartView(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setData(float[] data, int[] colors) {
        this.data = data;
        this.colors = colors;
        invalidate(); // Request a redraw
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data == null || colors == null || data.length != colors.length) {
            return;
        }

        float total = 0;
        for (float value : data) {
            total += value;
        }

        float startAngle = 0;
        for (int i = 0; i < data.length; i++) {
            paint.setColor(colors[i]);
            float sweepAngle = 360 * (data[i] / total);
            canvas.drawArc(100, 100, 500, 500, startAngle, sweepAngle, true, paint);
            startAngle += sweepAngle;
        }
    }
}


