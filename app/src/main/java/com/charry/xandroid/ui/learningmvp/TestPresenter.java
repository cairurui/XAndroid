package com.charry.xandroid.ui.learningmvp;

import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.ui.learningmvp.entity.LoginEntity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaocai on 2018/1/22.
 */

public class TestPresenter extends BasePresenter<TestView, TestModel> {


    public void doGet(String name, int age) {

        model.doGet(name, age)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<LoginEntity>() {
                    @Override
                    public void accept(LoginEntity loginEntity) throws Exception {
                        getView().onGetSuc(loginEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onGetFail(throwable);
                    }
                });

    }

    public void doPost(String name, String email) {

        model.doPost(name, email).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<LoginEntity>() {
                    @Override
                    public void accept(LoginEntity loginEntity) throws Exception {
                        getView().onPostSuc(loginEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onPostFail(throwable);
                    }
                });
    }
}
