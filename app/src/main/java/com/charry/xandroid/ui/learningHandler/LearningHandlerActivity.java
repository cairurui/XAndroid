package com.charry.xandroid.ui.learningHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.charry.xandroid.R;
import com.charry.xandroid.utils.Xlog;

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

        toolbarTitle.setText("handler 源码学习");

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }


    protected void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                changeText();
            }
        }).start();
    }

    Handler handler;

    private void changeText() {
        Looper.prepare();
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Xlog.m();
                tv.setText("changed");
            }
        });
        Looper.loop();
    }
}
