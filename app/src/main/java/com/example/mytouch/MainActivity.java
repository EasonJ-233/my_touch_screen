package com.example.mytouch;

/**
 * @Author: jiangyc
 * 需要实现的功能汇总
 * 1.关于手势的状态显示
 * 2.关于滑动距离的显示
 * 3.关于手指在X-Y方向的滑动速度的显示
 */

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private TextView actionTextView;
    private GestureDetector gestureDetector;
    private GestureDrawView gestureDrawView;
    private long startLongPressTime;
    private long endLongPressTime;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionTextView = findViewById(R.id.tv_desc);
        gestureDetector = new GestureDetector(getApplicationContext(), this);
        gestureDrawView = findViewById(R.id.gestureDrawView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.i("MyTouch", "onUp");
                String action = "单次长按时间：";
                endLongPressTime = System.currentTimeMillis();
                action = action + (endLongPressTime - startLongPressTime) + "ms";
                actionTextView.setText(action);
                break;
            // 其他触摸事件处理
        }
        //画板的动作
        gestureDrawView.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.i("MyTouch", "onDown");
        actionTextView.setText("手指按下");
        startLongPressTime = System.currentTimeMillis();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.i("MyTouch", "onShowPress");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.i("MyTouch", "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
        Log.i("MyTouch", "onScroll");
        String action = "慢滑";
        if (Math.abs(e1.getX() - e2.getX()) >= Math.abs(e1.getY() - e2.getY())) {
            // 横向滑动
            if (e1.getX() - e2.getX() > 10) {
                action = "手指慢慢向左滑动←";
                action = action + "；\n距离：" + Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2)) + " px；\nX轴方向距离：" + dx + "；\nY轴方向速度：" + dy;
            } else if (e2.getX() - e1.getX() > 10) {
                action = "手指慢慢向右滑动→";
                action = action + "；\n距离：" + Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2))  + " px；\nX轴方向距离：" + dx  + " px/s" + "；\nY轴方向速度：" + dy;
            }
        } else {
            // 纵向滑动
            if (e1.getY() - e2.getY() > 10) {
                action = "手指慢慢向上滑动↑";
                action = action + "；\n距离：" + Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2)) + " px；\nX轴方向距离：" + dx + " px/s" + "；\nY轴方向速度：" + dy;
            } else if (e2.getY() - e1.getY() > 10) {
                action = "手指慢慢向下滑动↓";
                action = action + "；\n距离：" + Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2)) + " px；\nX轴方向距离：" + dx + "；\nY轴方向速度：" + dy;
            }
        }
        actionTextView.setText(action);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.i("MyTouch", "onLongPress");
        actionTextView.setText("长按事件");
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i("MyTouch", "onFling");
        String action = "滑动行为";
        // 快速滑动事件，可以在这里判断大概的滑动方向
        if (Math.abs(e1.getX() - e2.getX()) >= Math.abs(e1.getY() - e2.getY())) {
            // 横向滑动
            if (e1.getX() - e2.getX() > 10) {
                action = "手指快速向左滑动←";
                action = action + "；\n距离：" + Math.sqrt(Math.pow(Math.abs(e1.getX()-e2.getX()),2) + Math.pow(Math.abs(e1.getY()-e2.getY()),2)) + " px；\nX轴方向速度：" + Math.abs(velocityX) + " px/s" + "；\nY轴方向速度：" + Math.abs(velocityY) + " px/s";
            } else if (e2.getX() - e1.getX() > 10) {
                action = "手指快速向右滑动→";
                action = action + "；\n距离：" + Math.sqrt(Math.pow(Math.abs(e1.getX()-e2.getX()),2) + Math.pow(Math.abs(e1.getY()-e2.getY()),2)) + " px；\nX轴方向速度：" + Math.abs(velocityX) + " px/s" + "；\nY轴方向速度：" + Math.abs(velocityY) + " px/s";
            }
        } else {
            // 纵向滑动
            if (e1.getY() - e2.getY() > 10) {
                action = "手指快速向上滑动↑";
                action = action + "；\n距离：" + Math.sqrt(Math.pow(Math.abs(e1.getX()-e2.getX()),2) + Math.pow(Math.abs(e1.getY()-e2.getY()),2)) + " px；\nX轴方向速度：" + Math.abs(velocityX) + " px/s" + "；\nY轴方向速度：" + Math.abs(velocityY) + " px/s";
            } else if (e2.getY() - e1.getY() > 10) {
                action = "手指快速向下滑动↓";
                action = action + "；\n距离：" + Math.sqrt(Math.pow(Math.abs(e1.getX()-e2.getX()),2) + Math.pow(Math.abs(e1.getY()-e2.getY()),2)) + " px；\nX轴方向速度：" + Math.abs(velocityX) + " px/s" + "；\nY轴方向速度：" + Math.abs(velocityY) + " px/s";
            }
        }
        actionTextView.setText(action);
        return true;
    }
}