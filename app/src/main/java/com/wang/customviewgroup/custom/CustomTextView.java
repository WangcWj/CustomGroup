package com.wang.customviewgroup.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.wang.customviewgroup.R;

/**
 * Created by Administrator on 2017/6/23.
 */

public class CustomTextView extends View {
    private int color;
    private float textSize;
    private String text;
    private Rect mbounds;
    private Paint mPaint;
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
   private void init(Context context, AttributeSet attrs){
       TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
      textSize =typedArray.getDimension(R.styleable.CustomTextView_textSize,14);
      text= typedArray.getString(R.styleable.CustomTextView_text);
      color= typedArray.getColor(R.styleable.CustomTextView_textColor, Color.parseColor("#333333"));
      typedArray.recycle();
       mPaint = new Paint();
       mPaint.setTextSize(textSize);
       mbounds = new Rect();
       mPaint.getTextBounds(text,0,text.length(),mbounds);
   }
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.AT_MOST){
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(text,0,text.length(),mbounds);
            width= mbounds.width()+getPaddingLeft()+getPaddingRight()+50;
        }
        if(heightMode == MeasureSpec.AT_MOST){
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(text,0,text.length(),mbounds);
            height = mbounds.height()+getPaddingBottom()+getPaddingBottom()+50;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#fe4646"));
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);

        mPaint.setColor(color);
        canvas.drawText(text,getWidth()/2-mbounds.width()/2,getHeight()/2+mbounds.height()/2,mPaint);
    }
}
