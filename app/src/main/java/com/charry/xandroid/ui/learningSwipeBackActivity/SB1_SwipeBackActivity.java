package com.charry.xandroid.ui.learningSwipeBackActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class SB1_SwipeBackActivity extends BaseActivity {

    private boolean canSwipeBack = true;

    @BindView(R.id.tv_swipe_back)
    TextView tv_swipe_back;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public Object getContentView() {
        return R.layout.activity_swipe_back;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setUpTitle("swipe_back activity 学习");
        setContentText();
    }


    @OnClick(R.id.tv_swipe_back)
    public void setChangeSwipeBack(TextView tv) {
        canSwipeBack = !canSwipeBack;
        setContentText();
        setSwipeBackEnable(canSwipeBack);
    }

    private void setContentText() {
        String sb = "当前" +
                (canSwipeBack ? "可以侧滑退出" : "不可以侧滑退出");
        tv_swipe_back.setText(sb);
    }


}
