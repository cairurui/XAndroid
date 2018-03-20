package com.charry.xandroid.base;

/**
 * Created by xiaocai on 2018/1/22.
 */

public interface IPresenter<V extends IView> {

    void attachView(V view);

    void detachView();
}
