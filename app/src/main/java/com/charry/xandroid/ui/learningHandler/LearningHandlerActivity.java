package com.charry.xandroid.ui.learningHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.charry.xandroid.R;
import com.charry.xandroid.utils.Xlog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LearningHandlerActivity extends AppCompatActivity {

    @BindView(R.id.btn_thread_one)
    Button btn_thread_one;
    @BindView(R.id.btn_thread_two)
    Button btn_thread_two;
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

        btn_thread_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadOne();
            }
        });
    }


    protected void threadOne() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                changeText();
            }
        }).start();
    }


    protected void threadTwo() {
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
                btn_thread_one.setText("changed");
            }
        });
        Looper.loop();
    }
}
