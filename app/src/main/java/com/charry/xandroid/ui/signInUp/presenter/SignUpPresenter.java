package com.charry.xandroid.ui.signInUp.presenter;

import android.util.Log;

import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.base.RxSchedulersHelper;
import com.charry.xandroid.ui.signInUp.model.SignUpBean;
import com.charry.xandroid.ui.signInUp.model.SignUpModel;
import com.charry.xandroid.ui.signInUp.view.SignUpView;

import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaocai on 2018/3/20.
 */

public class SignUpPresenter extends BasePresenter<SignUpView, SignUpModel> {


    public void signUp(String name, String pwd, String repwd) {
        Log.d("xiaocai", "signUp() called with: name = [" + name + "], pwd = [" + pwd + "], repwd = [" + repwd + "]");


        model.signUp(name, pwd, repwd)
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
                        Log.d("xiaocai",throwable.getMessage());
                    }
                });


    }


}
