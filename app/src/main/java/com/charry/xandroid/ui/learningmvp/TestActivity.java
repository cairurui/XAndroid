package com.charry.xandroid.ui.learningmvp;

import android.os.Bundle;
import android.widget.Button;


import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.ui.learningmvp.entity.LoginEntity;
import com.charry.xandroid.http.ApiClient;
import com.charry.xandroid.utils.Xlog;

import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends BaseActivity<TestPresenter> {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public Object getContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setUpTitle("mvp 学习 不使用 pm");
    }

    @OnClick(R.id.btn_get)
    public void onBtnGet(Button btn) {
        doGet();
    }


    @OnClick(R.id.btn_post)
    public void onBtnPost(Button btn) {
        doPost();
    }

    private void doGet() {
        ApiClient.getInstance().getApiService().doGet("xiaocai", 25)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<LoginEntity>() {
                    @Override
                    public void accept(LoginEntity loginEntity) throws Exception {
                        Xlog.d(loginEntity.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

    }

    private void doPost() {
        ApiClient.getInstance().getApiService().doPost("xiaocai", "xiaocai@163.com").observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<LoginEntity>() {
                    @Override
                    public void accept(LoginEntity loginEntity) throws Exception {
                        Xlog.d(loginEntity.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();

                    }
                });
    }

}
