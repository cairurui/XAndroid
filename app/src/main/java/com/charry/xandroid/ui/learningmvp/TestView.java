package com.charry.xandroid.ui.learningmvp;

import com.charry.xandroid.base.IView;
import com.charry.xandroid.ui.learningmvp.entity.LoginEntity;

/**
 * Created by xiaocai on 2018/1/22.
 */

public interface TestView extends IView {

    void onGetSuc(LoginEntity entity);
    void onGetFail(Throwable throwable);


    void onPostSuc(LoginEntity entity);
    void onPostFail(Throwable throwable);



}
