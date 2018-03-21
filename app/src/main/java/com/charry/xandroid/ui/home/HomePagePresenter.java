package com.charry.xandroid.ui.home;

import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.ui.home.entity.BannerBean;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by charry on 2018/3/21.
 */

public class HomePagePresenter extends BasePresenter<HomePageView, HomePageModel> {


    public void getBanner() {

        model.getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BannerBean>() {
                    @Override
                    public void accept(BannerBean bannerBean) throws Exception {
                        if (bannerBean.errorCode == 0 && bannerBean.data.size() > 0) {
                            getView().onBannerViewSuc(bannerBean.data);
                        } else {
                            getView().onBannerFail(bannerBean.errorMsg);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onBannerFail("获取banner失败");
                        throwable.printStackTrace();
                    }
                });


    }


}
