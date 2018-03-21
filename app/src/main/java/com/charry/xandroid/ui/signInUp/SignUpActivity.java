package com.charry.xandroid.ui.signInUp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.ui.signInUp.model.SignUpBean;
import com.charry.xandroid.ui.signInUp.presenter.SignUpPresenter;
import com.charry.xandroid.ui.signInUp.view.SignUpView;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity<SignUpPresenter> implements SignUpView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_re_pwd)
    EditText etRePwd;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected SignUpPresenter createPresenter() {
        return new SignUpPresenter();
    }

    @Override
    public Object getContentView() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void signUpFail(String msg) {
        showMessage(msg);
    }

    @Override
    public void signUpSuc(SignUpBean.DataBean bean) {
        showMessage("注册成功");
        finish();
    }

    @OnClick(R.id.btn_register)
    public void clickToRegister(View v) {
        try {
            checkInput(etName, "请检查用户名");
            checkInput(etPwd, "请检查密码");
            checkInput(etRePwd, "请检查第二次密码");
            if (!etPwd.getText().toString().equals(etRePwd.getText().toString()))
                showMessage("两次输入密码不一致");

            getPresenter().signUp(
                    etName.getText().toString().trim(),
                    etPwd.getText().toString().trim(),
                    etRePwd.getText().toString().trim()
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
