package com.wang.customviewgroup.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wang.customviewgroup.R;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener{
   Button textview,customViewGroup,customImageView,customMoveView,customRingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        textview = (Button) findViewById(R.id.customTextView);
        customViewGroup = (Button) findViewById(R.id.customViewGroup);
        customImageView = (Button) findViewById(R.id.customImageView);
        customMoveView = (Button) findViewById(R.id.customMoveView);
        customRingView = (Button) findViewById(R.id.customRingView);
        textview.setOnClickListener(this);
        customViewGroup.setOnClickListener(this);
        customImageView.setOnClickListener(this);
        customMoveView.setOnClickListener(this);
        customRingView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.customTextView:
             Intent intent =new Intent(this,CustomTextViewActivity.class);
             startActivity(intent);
             break;
         case R.id.customViewGroup:
             Intent intent2 =new Intent(this,CustomGroupActivity.class);
             startActivity(intent2);
             break;
         case R.id.customImageView:
             Intent intent3 =new Intent(this,CustomImageActivity.class);
             startActivity(intent3);
             break;
         case R.id.customMoveView:
             Intent intent4 =new Intent(this,MoveActivity.class);
             startActivity(intent4);
             break;
         case R.id.customRingView:
             Intent intent5 =new Intent(this,RingProgressActivity.class);
             startActivity(intent5);
             break;
     }
    }
}
