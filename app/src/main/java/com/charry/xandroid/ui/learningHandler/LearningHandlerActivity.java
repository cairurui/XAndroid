package com.charry.xandroid.ui.learningHandler;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.base.BasePresenter;

import butterknife.BindView;

public class LearningHandlerActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public Object getContentView() {
        return R.layout.activity_learning_handler;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setUpTitle("handler 源码学习");
    }

    @Override
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
