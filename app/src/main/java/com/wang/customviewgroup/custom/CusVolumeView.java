package com.wang.customviewgroup.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/6/29.
 */

public class CusVolumeView extends View {
    //第一圈的颜色
    private int mFirstColor = Color.RED;
    //第二圈的颜色
    private int mSendColor = Color.GREEN;
    //小椭圆的数量
    private int reactCount =16;
    //中间的间隙
    private int spliteSize = 20;
    private int width = 10;
    Paint paint = new Paint();
     private int currentCount =0;
    public CusVolumeView(Context context) {
        super(context);
    }

    public CusVolumeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置为空心
        paint.setStyle(Paint.Style.STROKE);
        //设置边线的宽度
        paint.setStrokeWidth(width);
        //这是为椭圆边距
        paint.setStrokeCap(Paint.Cap.ROUND);

        int center = getWidth() /2;
        int radius = center - width/2;

        RectF oval = new RectF(center - radius,center-radius,center+radius,center+radius);
        float itemSize = (360 * 1.0f - reactCount * spliteSize) / reactCount;
        paint.setColor(mFirstColor);
        for (int i = 0; i <reactCount ; i++) {
            canvas.drawArc(oval,i*(itemSize+spliteSize),itemSize,false,paint);
        }
        paint.setColor(mSendColor);
        for (int i = 0; i < currentCount ; i++) {
              canvas.drawArc(oval,i*(itemSize+spliteSize),itemSize,false,paint);
        }
    }
    /**
     * 当前数量+1
     */
    public void up()
    {
            currentCount++;
        if(currentCount <= reactCount)
            postInvalidate();
    }

    /**
     * 当前数量-1
     */
    public void down()
    {
            currentCount--;
          if(currentCount >=0)
            postInvalidate();
    }

    private int xDown, xUp;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();
                break;

            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                if (xUp > xDown)// 下滑
                {
                    down();
                } else
                {
                    up();
                }
                break;
        }

        return true;
    }
}
