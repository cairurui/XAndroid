package com.charry.xandroid.ui.home.fragment;


import android.os.Bundle;
import android.view.View;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseFragment;
import com.charry.xandroid.base.BasePresenter;

/**
 * A fragment with a Google +1 button.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment {


    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance() {
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setSwipeBackEnable(false);
    }
}
