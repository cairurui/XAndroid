package com.charry.xandroid.ui.home.mvp;

import com.charry.xandroid.base.IView;
import com.charry.xandroid.ui.home.entity.BannerBean;

import java.util.List;

import javax.crypto.spec.IvParameterSpec;

/**
 * Created by charry on 2018/3/21.
 */

public interface HomePageView extends IView {

    void onBannerViewSuc(List<BannerBean.DataBean> data);

    void onBannerFail(String errorMsg);
}
