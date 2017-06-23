package com.wang.customviewgroup.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.wang.customviewgroup.R;

/**
 * Created by Administrator on 2017/6/23.
 */

public class CustomImageView extends View {
    private float textSize;
    private int color;
    private String text;
    private Bitmap iamge;
    private int scalleType;
    private Paint mPaint;
    private Rect mBounds;
    private Rect rect;
    private int mWidth = 0;
    private int mHeight = 0;
    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    private  void init(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
        color= typedArray.getColor(R.styleable.CustomImageView_titleTextColor, Color.parseColor("#333333"));
        text=typedArray.getString(R.styleable.CustomImageView_titleText);
        textSize = typedArray.getDimension(R.styleable.CustomImageView_titleTextSize,14);
        scalleType = typedArray.getInteger(R.styleable.CustomImageView_imageScaleType,1);
        iamge = BitmapFactory.decodeResource(getResources(),R.mipmap.timg);
        typedArray.recycle();
        mPaint = new Paint();
        mBounds = new Rect();
        rect = new Rect();
        mPaint.setTextSize(textSize);
        mPaint.getTextBounds(text,0,text.length(),mBounds);
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
            mPaint.getTextBounds(text,0,text.length(),mBounds);
            int width1 = mBounds.width();
            int width2 = iamge.getWidth();
            mWidth = Math.max(width1,width2)+getPaddingRight()+getPaddingLeft();
        }
        if(heightMode == MeasureSpec.AT_MOST){
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(text,0,text.length(),mBounds);
            int height1 = mBounds.height();
            int height2 = iamge.getHeight();
            mHeight= getPaddingBottom()+getPaddingTop()+height1+height2;
        }
       setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#00ff00"));
        mPaint.setAntiAlias(false);
        mPaint.setStrokeWidth(2);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        rect.left=getPaddingLeft();
        rect.right=mWidth-getPaddingRight();
        rect.top=getPaddingTop();
        rect.bottom = mHeight-getPaddingBottom();

        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        /**
         * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
         */
        if (mBounds.width() > mWidth)
        {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(text, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

        } else
        {
            //正常情况，将字体居中
            canvas.drawText(text, mWidth / 2 - mBounds.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }

        //取消使用掉的快
        rect.bottom -= mBounds.height();
        if(scalleType == 0){
            canvas.drawBitmap(iamge,null,rect,mPaint);
       }else {
           rect.left = mWidth/2 - iamge.getWidth()/2;
           rect.top = (mHeight-mBounds.height())/2 - iamge.getHeight() /2;
           rect.right = mWidth/2+iamge.getWidth()/2;
           rect.bottom = (mHeight-mBounds.height())/2 + iamge.getHeight() /2;

           canvas.drawBitmap(iamge,null,rect,mPaint);
       }
    }
}
