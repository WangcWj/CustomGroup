package com.wang.customviewgroup.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.wang.customviewgroup.R;

/**
 * Created by Administrator on 2017/6/29.
 */

public class CustomRingProgress extends View {
    //第一圈的颜色
    int mFirstColor;
    //第二圈的颜色
    int mSecondColor;
    //圆环的宽度
    int mCircleWidth;
    //圆环的进度
    int mProgress;
    //圆环的速度
    int speed;
    //是否开始下一圈
    boolean isNext;
    Paint mPaint;
    public CustomRingProgress(Context context) {
        super(context);
    }

    public CustomRingProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomRingProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        mFirstColor = typedArray.getColor(R.styleable.CustomProgressBar_firstColor, Color.RED);
        mSecondColor = typedArray.getColor(R.styleable.CustomProgressBar_secondColor,Color.GREEN);
        mCircleWidth = typedArray.getDimensionPixelSize(R.styleable.CustomProgressBar_circleWidth, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        speed = typedArray.getInteger(R.styleable.CustomProgressBar_speed, 20);
        typedArray.recycle();
        mPaint = new Paint();
        // 绘图线程
        new Thread()
        {
            public void run()
            {
                while (true)
                {
                    mProgress++;
                    if (mProgress == 360)
                    {
                        mProgress = 0;
                        if (!isNext)
                            isNext = true;
                        else
                            isNext = false;
                    }
                    postInvalidate();
                    try
                    {
                        Thread.sleep(speed);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() /2;
        int radius = center - mCircleWidth;
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius); // 用于定义的圆弧的形状和大小的界限
        //画第一圈
        if(!isNext){
            mPaint.setColor(mFirstColor);
            //第一个参数是圆心的x坐标
            //第二个参数是圆心的y坐标
            //第三个是半径
            canvas.drawCircle(center,center,radius,mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval,-90,mProgress, false, mPaint);
        }else {
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawCircle(center, center, radius, mPaint); // 画出圆环
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            //第一个参数矩形区域椭圆形的界限用于定义在形状、大小、电弧.
            // 第二个参数是起始角度在电弧中的开始.
            //第三个参数是扫描角度开始顺时针测量的.
            // 第四个参数这是真的话,包括椭圆中心的电弧,并关闭它,如果它是假这将是一个弧线,参数五是Paint对象
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        }
    }
}
