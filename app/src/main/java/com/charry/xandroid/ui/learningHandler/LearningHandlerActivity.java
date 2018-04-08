package com.charry.xandroid.ui.learningHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.charry.xandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LearningHandlerActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_handler);
        ButterKnife.bind(this);
        toolbarTitle.setText("Handler 学习");
        initData();

    }

    protected void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                changeText();
            }
        }).start();
    }

    private void changeText() {
        Looper.prepare();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                tv.setText("changed");
            }
        });
        Looper.loop();
    }
}
