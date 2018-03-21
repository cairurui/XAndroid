package com.charry.xandroid.ui.signInUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.MainActivity;
import com.charry.xandroid.ui.signInUp.model.SignUpBean;
import com.charry.xandroid.ui.signInUp.presenter.SignInPresenter;
import com.charry.xandroid.ui.signInUp.view.SignInView;
import com.charry.xandroid.utils.Constent;
import com.charry.xandroid.utils.SPUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity<SignInPresenter> implements SignInView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.ty_register)
    TextView tyRegister;

    @Override
    protected BasePresenter createPresenter() {
        return new SignInPresenter();
    }

    @Override
    public Object getContentView() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @Override
    public void signUpSuc(SignUpBean.DataBean data) {
        hideLoading();
        // 保存 用户 id
        SPUtils.getInstance().put(Constent.USER_INFO, new Gson().toJson(data));
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void signUpFail(String errorMsg) {
        hideLoading();
        showMessage(errorMsg);
    }

    @OnClick(R.id.ty_register)
    public void clickToRegister(View v) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @OnClick(R.id.btn_login)
    public void clickToLogin(View v) {
        showLoading();

        try {
            checkInput(etName, "请检查用户名");
            checkInput(etPwd, "请检查密码");

            getPresenter().signIn(
                    etName.getText().toString().trim(),
                    etPwd.getText().toString().trim()
            );
        } catch (Exception e) {
            showMessage(e.getMessage());
        }

    }

    private void checkInput(EditText input, String msg) {
        String content = input.getText().toString().trim();
        if (TextUtils.isEmpty(content))
            throw new IllegalStateException(msg);
        if (content.length() < 6)
            throw new IllegalStateException(msg);

    }


}
