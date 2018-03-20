package com.charry.xandroid.ui.learningmvp;

import com.charry.xandroid.base.BaseModel;
import com.charry.xandroid.ui.learningmvp.entity.LoginEntity;
import com.charry.xandroid.http.ApiClient;

import io.reactivex.Observable;

/**
 * Created by xiaocai on 2018/1/22.
 */

public class TestModel extends BaseModel {

    public Observable<LoginEntity> doGet(String name, int age) {
        return ApiClient.getInstance().getApiService().doGet(name, age);

    }

    public Observable<LoginEntity> doPost(String name, String email) {
        return ApiClient.getInstance().getApiService().doPost(name, email);
    }

}
