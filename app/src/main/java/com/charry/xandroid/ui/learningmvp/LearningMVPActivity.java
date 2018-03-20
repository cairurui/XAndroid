package com.charry.xandroid.ui.learningmvp;

import android.os.Bundle;
import android.widget.Button;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.ui.learningmvp.entity.LoginEntity;
import com.charry.xandroid.utils.UIUtil;
import com.charry.xandroid.utils.Xlog;

import butterknife.OnClick;

public class LearningMVPActivity extends BaseActivity<TestPresenter> implements TestView {

    @Override
    protected BasePresenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    public Object getContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setUpTitle("mvp 学习 完全使用 mvp");
    }

    @OnClick(R.id.btn_get)
    public void onBtnGet(Button btn) {
        getPresenter() .doGet("xiaocai",25);
    }

    @OnClick(R.id.btn_post)
    public void onBtnPost(Button btn) {
        getPresenter() .doPost("xiaocai", "xiaocai@163.com");
    }

    @Override
    public void onGetSuc(LoginEntity entity) {
        Xlog.d(entity.toString());
    }

    @Override
    public void onGetFail(Throwable throwable) {
        UIUtil.toash(throwable.getMessage());
    }

    @Override
    public void onPostSuc(LoginEntity entity) {
        Xlog.d(entity.toString());
    }

    @Override
    public void onPostFail(Throwable throwable) {
        UIUtil.toash(throwable.getMessage());
    }
}
