package com.charry.xandroid.ui.signInUp.model;

import com.charry.xandroid.base.BaseModel;
import com.charry.xandroid.http.ApiClient;

import io.reactivex.Observable;


/**
 * Created by xiaocai on 2018/3/20.
 */

public class SignInUpModel extends BaseModel {

    public Observable<SignUpBean> signUp(String name, String pwd, String repwd) {
        return ApiClient.getInstance().getApiService().signUp(name, pwd, repwd);

    }

    public Observable<SignUpBean> signIn(String name, String pwd ) {
        return ApiClient.getInstance().getApiService().signIn(name, pwd );

    }


}
