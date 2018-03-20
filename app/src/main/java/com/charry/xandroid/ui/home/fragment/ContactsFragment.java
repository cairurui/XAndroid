package com.charry.xandroid.ui.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseFragment;
import com.charry.xandroid.base.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment {


    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance() {
        ContactsFragment messageFragment = new ContactsFragment();
        return messageFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
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
