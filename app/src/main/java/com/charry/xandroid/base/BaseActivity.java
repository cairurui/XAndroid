package com.charry.xandroid.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


import com.charry.xandroid.R;
import com.charry.xandroid.utils.CommonDialog;
import com.charry.xandroid.utils.NetworkUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by xiaocai on 2018/1/19.
 */

public abstract class BaseActivity<P extends BasePresenter> extends SwipeBackActivity implements IView {


    protected P mPresenter;
    private Unbinder mUnBinder;
    public String TAG;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getComponentName().getShortClassName();

        Object contentView = getContentView();
        if (contentView instanceof View) {
            setContentView((View) getContentView());
        } else {
            setContentView((int) getContentView());
        }

        mUnBinder = ButterKnife.bind(this);

        mPresenter = (P) createPresenter();
        if (mPresenter != null)
            mPresenter.attachView(this);

        initView(savedInstanceState);
        initData();


    }

    protected P getPresenter() {
        return mPresenter;
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected void initData() {
    }

    protected abstract BasePresenter createPresenter();

    public abstract Object getContentView();

    protected Toolbar toolbar;
    protected TextView toolbar_title;

    protected void setUpTitle(String title) {
        setUpTitle(title, true);
    }

    protected void setUpTitle(String title, boolean hasToolbarBack) {
        // toolbar_title 不为 null 则说明已经加载过
        if (toolbar_title != null) {
            toolbar_title.setText(title);
            return;
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

            if (hasToolbarBack) {
                toolbar.setNavigationIcon(R.drawable.ic_toolbar_back);
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationBtnClicked();
                }
            });

            if (toolbar_title != null) {
                toolbar_title.setText(title);
            }

            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowTitleEnabled(false);
            }
        }
    }

    protected void onNavigationBtnClicked() {
        finish();
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtil.isNetworkEnable(getApplicationContext());
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonDialog.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter!=null){
            mPresenter.detachView();
        }
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
        mProgressDialog = null;
    }
}
