package com.charry.xandroid.ui.signInUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {

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
        return null;
    }

    @Override
    public Object getContentView() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.ty_register)
    public void clickToRegister(View v){
        startActivity(new Intent(this,SignUpActivity.class));
    }


}
