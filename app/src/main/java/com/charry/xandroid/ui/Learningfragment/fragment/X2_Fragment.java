package com.charry.xandroid.ui.Learningfragment.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseFragment;
import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.ui.Learningfragment.LearningFragmentActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class X2_Fragment extends BaseFragment {


    @BindView(R.id.tv_fragment)
    TextView tv_fragment;

    LearningFragmentActivity mActivity;

    public X2_Fragment() {
        // Required empty public constructor
    }

    public static X2_Fragment newInstance() {
        return new X2_Fragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tv_fragment.setText(getClass().getSimpleName());
        mActivity = (LearningFragmentActivity) getActivity();
    }

    @OnClick(R.id.tv_fragment)
    public void onFragmentClick(TextView tv){

    }
}
