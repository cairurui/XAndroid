package com.charry.xandroid.ui.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.charry.xandroid.R;

public class LearningCustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_custom_view);


        LevelView levelView = findViewById(R.id.level_view);
        levelView.setRange(0.5f);
        levelView.setLeftText("500");

    }
}
