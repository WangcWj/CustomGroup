package com.wang.customviewgroup.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/6/26.
 */

public class ImageMoveView extends ImageView {
    int lastX;
    int lastY;
    int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
    int screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
    public ImageMoveView(Context context) {
        super(context);
    }

    public ImageMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("CHAO", "onTouchEvent: "+screenHeight );
             switch (event.getAction()){
                 case MotionEvent.ACTION_DOWN:
                     lastX = (int) event.getRawX();
                     lastY = (int) event.getRawY();
                     break;
                 case MotionEvent.ACTION_MOVE:
                     int dx = (int) (event.getRawX() - lastX);
                     int dy = (int )(event.getRawY() - lastY);
                     int left = getLeft() + dx;
                     int right = getRight()+dx;
                     int top = getTop()+dy;
                     int bottom = getBottom() +dy;
                     if(left <0 ){
                         left =0;
                         right = getWidth()+left;
                     }
                     if(right > screenWidth){
                         right = screenWidth;
                         left = screenWidth - getWidth();
                     }
                     if(top < 0){
                         top = 0;
                         bottom = top+getHeight();
                     }
                     if(bottom > screenHeight){
                         bottom = screenHeight;
                         top = screenHeight -getHeight();
                     }
                     layout(left,top,right,bottom);
                     lastX = (int) event.getRawX();
                     lastY = (int) event.getRawY();
                     break;
             }
        return true;
    }
}
