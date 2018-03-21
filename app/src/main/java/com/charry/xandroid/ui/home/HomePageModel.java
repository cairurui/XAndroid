package com.charry.xandroid.ui.home;

import com.charry.xandroid.base.BaseModel;
import com.charry.xandroid.http.ApiClient;
import com.charry.xandroid.ui.home.entity.BannerBean;

import io.reactivex.Observable;

/**
 * Created by charry on 2018/3/21.
 */

public class HomePageModel extends BaseModel {

    public Observable<BannerBean> getBanner() {
        return ApiClient.getInstance().getApiService().getBanner();
    }


}
