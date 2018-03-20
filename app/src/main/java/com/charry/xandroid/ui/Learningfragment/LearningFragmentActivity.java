package com.charry.xandroid.ui.Learningfragment;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.base.BaseFragment;
import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.ui.Learningfragment.fragment.X1_Fragment;

import butterknife.BindView;

public class LearningFragmentActivity extends BaseActivity {

    @BindView(R.id.fl_container)
    FrameLayout fl_container;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public Object getContentView() {
        return R.layout.activity_learning_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setUpTitle("fragment 的切换 主页面");
        if (findFragment(X1_Fragment.class) == null) {
            loadRootFragment(R.id.fl_container, X1_Fragment.newInstance());
        }
    }

    public void pushFragment(BaseFragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
//        beginTransaction.add(R.id.fl_container, fragment);
//    //    beginTransaction.replace(R.id.fl_container, fragment);
//        beginTransaction.addToBackStack(null);
//        beginTransaction.commit();


        start(fragment);
    }

//    @Override
//    public void onBackPressedSupport() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            super.onBackPressedSupport();
//        }
//    }
//
//    @Override
//    protected void onNavigationBtnClicked() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            super.onNavigationBtnClicked();
//        }
//    }
}
