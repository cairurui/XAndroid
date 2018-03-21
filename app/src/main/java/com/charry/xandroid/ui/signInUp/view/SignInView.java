package com.charry.xandroid.ui.signInUp.view;

import com.charry.xandroid.base.IView;
import com.charry.xandroid.ui.signInUp.model.SignUpBean;


/**
 * Created by xiaocai on 2018/3/20.
 */

public interface SignInView extends IView {


    void signUpSuc(SignUpBean.DataBean data);


    void signUpFail(String errorMsg);
}
