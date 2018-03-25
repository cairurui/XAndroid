package com.charry.xandroid.http;

import com.charry.xandroid.MyApplication;
import com.charry.xandroid.utils.NetworkUtil;
import com.charry.xandroid.utils.UIUtil;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * Created by ChenGD on 2017/5/19.
 */
public class NoNetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetworkUtil.isNetworkEnable(MyApplication.getApplication())) {

            Observable.just(null).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    UIUtil.toash("未连接网络");
                }
            });


        }
        return chain.proceed(chain.request());
    }
}
