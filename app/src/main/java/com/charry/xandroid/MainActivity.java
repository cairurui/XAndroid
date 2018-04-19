package com.charry.xandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.ui.changeskin.ChangeSkinActivity;
import com.charry.xandroid.ui.customview.LearningCustomViewActivity;
import com.charry.xandroid.ui.home.fragment.ContactsFragment;
import com.charry.xandroid.ui.home.fragment.FindFragment;
import com.charry.xandroid.ui.home.fragment.HomeFragment;
import com.charry.xandroid.ui.home.fragment.MineFragment;
import com.charry.xandroid.ui.learningHandler.LearningHandlerActivity;
import com.charry.xandroid.widget.alphatabs.AlphaTabsIndicator;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    public Object getContentView() {
        setTheme(R.style.AppTheme);
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSwipeBackEnable(false);
        ViewPager mViewPger = (ViewPager) findViewById(R.id.mViewPager);
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPger.setAdapter(mainAdapter);
        mViewPger.addOnPageChangeListener(mainAdapter);

        alphaTabsIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
        alphaTabsIndicator.setViewPager(mViewPger);

        alphaTabsIndicator.getTabView(0).showNumber(6);
        alphaTabsIndicator.getTabView(1).showNumber(888);
        alphaTabsIndicator.getTabView(2).showNumber(88);
        alphaTabsIndicator.getTabView(3).showPoint();

        mViewPger.setOffscreenPageLimit(4);

        new RxPermissions(this)
                .request("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE")
                .subscribe();

        startOtherActivity();
    }

    private void startOtherActivity() {
        startActivity(new Intent(this, LearningCustomViewActivity.class));
        finish();
    }


    private AlphaTabsIndicator alphaTabsIndicator;

    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        private List<Fragment> fragments = new ArrayList<>();
        private String[] titles = {"微信", "通讯录", "发现", "我"};

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(HomeFragment.newInstance());
            fragments.add(ContactsFragment.newInstance());
            fragments.add(FindFragment.newInstance());
            fragments.add(MineFragment.newInstance());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (0 == position) {
                alphaTabsIndicator.getTabView(0).showNumber(alphaTabsIndicator.getTabView(0).getBadgeNumber() - 1);
            } else if (2 == position) {
                alphaTabsIndicator.getCurrentItemView().removeShow();
            } else if (3 == position) {
                alphaTabsIndicator.removeAllBadge();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
