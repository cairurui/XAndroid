package com.charry.xandroid.ui.customview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.charry.xandroid.R;

public class LearningCustomViewActivity extends AppCompatActivity {

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_custom_view);

        final ShapeView shapeView = findViewById(R.id.shape_view);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shapeView.exchange();

                mHandler.postDelayed(this, 1000);
            }
        }, 1000);


      RatingBar rating_bar =   findViewById(R.id.rating_bar);
        rating_bar.setCurrentGrade(3);
    }
}
