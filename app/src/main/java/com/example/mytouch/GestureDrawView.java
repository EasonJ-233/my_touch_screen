package com.example.mytouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GestureDrawView extends View {

    private Paint paint;
    private float lastX, lastY;
    private float currentX, currentY;
    private boolean isDrawing;

    public GestureDrawView(Context context) {
        super(context);
        init();
    }

    public GestureDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GestureDrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xFF0000FF); //蓝色
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        isDrawing = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isDrawing) {
            canvas.drawLine(lastX, lastY, currentX, currentY, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                lastX = x;
                lastY = y;
                currentX = x;
                currentY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDrawing) {
                    currentX = x;
                    currentY = y;
                    invalidate(); // 请求重绘
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDrawing = false;
                break;
        }

        return false;
    }
}
