package com.charry.xandroid.ui.signInUp.presenter;

import android.util.Log;

import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.ui.signInUp.model.SignUpBean;
import com.charry.xandroid.ui.signInUp.model.SignUpModel;
import com.charry.xandroid.ui.signInUp.view.SignInView;
import com.charry.xandroid.ui.signInUp.view.SignUpView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaocai on 2018/3/20.
 */

public class SignInPresenter extends BasePresenter<SignInView, SignUpModel> {


    public void signIn(String name, String pwd) {

        model.signIn(name, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SignUpBean>() {
                    @Override
                    public void accept(SignUpBean bean) throws Exception {
                        if (bean.errorCode == 0)
                            getView().signUpSuc(bean.data);
                        else
                            getView().signUpFail(bean.errorMsg);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().signUpFail(throwable.getMessage());
                        Log.d("xiaocai", throwable.getMessage());
                    }
                });


    }


}
