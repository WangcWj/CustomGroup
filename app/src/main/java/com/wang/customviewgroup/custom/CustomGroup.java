package com.wang.customviewgroup.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.wang.customviewgroup.R;

/**
 * Created by Administrator on 2017/6/22.
 */

public class CustomGroup extends ViewGroup {
    //重叠的尺寸
    private int scaleWidth ;
    public CustomGroup(Context context) {
        super(context);
    }

    public CustomGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRroup);
        scaleWidth = (int) typedArray.getDimension(R.styleable.CustomRroup_scaleWidth,dp2px(10));
        typedArray.recycle();
    }

    public CustomGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRroup);
        scaleWidth = (int) typedArray.getDimension(R.styleable.CustomRroup_scaleWidth,dp2px(10));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

         //子view排成一行的宽度
        int width = 0;
        //子view排成一行的高度
        int height = 0;
        int rawHeight =0;
        int rawWidth = 0;
         //有几个子view
        int count = getChildCount();
        //记录一行的view数量
        int index = 0;
        for (int i = 0; i <count ; i++) {
            View childAt = getChildAt(i);
            if(childAt.getVisibility() == GONE){
                if(index < count-1) {
                    width = Math.max(width, rawWidth);
                    height += rawHeight;
                }
                continue;
            }
            measureChildWithMargins(childAt,widthMeasureSpec,0,heightMeasureSpec,0);
            MarginLayoutParams lp  = (MarginLayoutParams) childAt.getLayoutParams();
            int childWidth = lp.leftMargin +lp.rightMargin+childAt.getMeasuredWidth();
            int childHeight = lp.topMargin + lp.bottomMargin + childAt.getMeasuredHeight();
            //到下一个组件的时候做出判断,还要减去最后一个View折叠的部分
            if(rawWidth+childWidth-(index >0 ? scaleWidth : 0) > parentWidth - getPaddingLeft()-getPaddingRight()){
                //换行
                width = Math.max(width,rawWidth);
                height += rawHeight;
                rawWidth = childWidth;
                rawHeight = childHeight;
                index= 0;
            }else {
                rawHeight = Math.max(childHeight,rawHeight);
                rawWidth+=childWidth;
                //每个子View都要减去重叠的那部分
                if(index >0){
                    rawWidth -= scaleWidth;
                }
            }
            //最后一个还没计算
            if(i == count -1){
                width = Math.max(rawWidth,width);
                height +=rawHeight;
            }
            index++;
        }
        setMeasuredDimension(
                widthMode == MeasureSpec.EXACTLY ? parentWidth : width + getPaddingLeft() + getPaddingRight(),
                heightMode == MeasureSpec.EXACTLY ? parentHeight : height + getPaddingTop() + getPaddingBottom()
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("CHAO", "onLayout: "+scaleWidth );
        int count = getChildCount();
        //父view的总宽度
        int viewWidth = getWidth() -getPaddingRight()-getPaddingLeft();
        //子view的右边界
        int maxWidth =viewWidth;
        //子view距离顶部的高度
        int maxHeight = 0;
        //父组件的padingTop
        int topOfset =getPaddingTop();
        //记录 第几个字view
        int index = 0;
        for (int i = 0; i <count ; i++) {
            View childAt = getChildAt(i);
            if(childAt.getVisibility() == GONE){
                continue;
            }
            MarginLayoutParams lp = (MarginLayoutParams) childAt.getLayoutParams();
            int childWidth = lp.leftMargin +lp.rightMargin +childAt.getMeasuredWidth();
            int childHeight = lp.topMargin+lp.bottomMargin +childAt.getMeasuredHeight();
            //从右往左排,view的右边距小于view的宽度时换行
            if(childWidth > maxWidth){
                   //换行
                topOfset+=maxHeight;
                maxHeight =0;
                index = 0;
                maxWidth =viewWidth;
            }
            int left = maxWidth - childWidth;
            int top = topOfset+lp.topMargin;
            int right = maxWidth;
            int bottom = topOfset +lp.topMargin+ childAt.getMeasuredHeight();
            childAt.layout(left,top,right,bottom);

            maxWidth -=childWidth;
            if(index != count -1){
                maxWidth += scaleWidth;
            }
            maxHeight = Math.max(maxHeight,childHeight);
            index++;
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
    public float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
