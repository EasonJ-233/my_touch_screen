package com.example.mytouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GestureDrawView extends View {

    private Paint mLinePaint;
    private Paint mArrowPaint;
    private Path mCurrentPath;
    private float lastX, lastY;
    private float currentX, currentY;
    private boolean isDrawingLine = false;

    //箭头画方向
    private float arrowSize = 40; // 箭头大小

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
        //画线的画笔
        mLinePaint = new Paint();
        mLinePaint.setColor(0xFF0000FF); //蓝色线
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(5);

        //画箭头的画笔
        mArrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArrowPaint.setStyle(Paint.Style.FILL);
        mArrowPaint.setColor(0xFFFF0000); // 红色方向箭头

        // 初始化路径
        mCurrentPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mCurrentPath, mLinePaint);
        if (isDrawingLine) {
            drawArrowLine(canvas, lastX, lastY, currentX, currentY, arrowSize);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCurrentPath.moveTo(x, y);
                lastX = x;
                lastY = y;
                //取消画直线的命令
                isDrawingLine = false;
               return false;
            case MotionEvent.ACTION_MOVE:
                mCurrentPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //手抬起的时候就做路径的清除
                currentX = x;
                currentY = y;
                //使能画直线的命令
                isDrawingLine = true;
                mCurrentPath.reset();
                break;
            default:
                return false;
        }
        invalidate(); // 请求重绘
        //点击事件不拦截，继续向上传递
        return false;
    }

    private void drawArrowLine(Canvas canvas, float startX, float startY, float stopX, float stopY, float arrowSize) {
        // 绘制直线
        canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);

        // 计算箭头方向向量
        double angle = Math.atan2(stopY - startY, stopX - startX);

        // 计算箭头尖端的坐标
        float arrowPointX = (float) (stopX - arrowSize * Math.cos(angle));
        float arrowPointY = (float) (stopY - arrowSize * Math.sin(angle));

        // 计算箭头两翼的坐标
        float arrowLeftX = (float) (arrowPointX - arrowSize * Math.cos(angle - Math.PI / 6));
        float arrowLeftY = (float) (arrowPointY - arrowSize * Math.sin(angle - Math.PI / 6));
        float arrowRightX = (float) (arrowPointX - arrowSize * Math.cos(angle + Math.PI / 6));
        float arrowRightY = (float) (arrowPointY - arrowSize * Math.sin(angle + Math.PI / 6));

        // 绘制箭头
        Path arrowPath = new Path();
        arrowPath.moveTo(arrowPointX, arrowPointY);
        arrowPath.lineTo(arrowLeftX, arrowLeftY);
        arrowPath.lineTo(stopX, stopY);
        arrowPath.lineTo(arrowRightX, arrowRightY);
        arrowPath.close();

        canvas.drawPath(arrowPath, mArrowPaint);
    }
}
