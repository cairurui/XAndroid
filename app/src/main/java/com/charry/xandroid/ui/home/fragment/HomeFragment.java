package com.charry.xandroid.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseFragment;
import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.ui.Learningfragment.LearningFragmentActivity;
import com.charry.xandroid.ui.home.adapter.HomeFragmentAdapter;
import com.charry.xandroid.ui.home.entity.HomeItemEntity;
import com.charry.xandroid.ui.learningConstraintLayout.LearningConstraintLayoutActivity;
import com.charry.xandroid.ui.learningHeart.learningHeartActivity;
import com.charry.xandroid.ui.learningSwipeBackActivity.SB1_SwipeBackActivity;
import com.charry.xandroid.ui.learningWebview.learningWebviewActivity;
import com.charry.xandroid.ui.learningmvp.LearningMVPActivity;
import com.charry.xandroid.ui.useprogresslayout.UseProgressLayoutListActivity;
import com.charry.xandroid.widget.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_home)
    RecyclerView rv_home;

    @BindView(R.id.loading_container)
    ProgressLayout loading_container;

    List<HomeItemEntity> mList;
    private HomeFragmentAdapter mHomeFragmentAdapter;

    {
        mList = new ArrayList<>();
        mList.add(new HomeItemEntity("fragment切换", LearningFragmentActivity.class));
        mList.add(new HomeItemEntity("mvp使用", LearningMVPActivity.class));
        mList.add(new HomeItemEntity("状态图的使用", UseProgressLayoutListActivity.class));
        mList.add(new HomeItemEntity("swipe_back activity 学习", SB1_SwipeBackActivity.class));
        mList.add(new HomeItemEntity("学习 constrainlayout", LearningConstraintLayoutActivity.class));
        mList.add(new HomeItemEntity("学习 webview", learningWebviewActivity.class));
        mList.add(new HomeItemEntity("学习 心跳包机制", learningHeartActivity.class));
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setSwipeBackEnable(false);
        rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeFragmentAdapter = new HomeFragmentAdapter(R.layout.item_home_fragment, mList);
        rv_home.setAdapter(mHomeFragmentAdapter);

        mHomeFragmentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), mList.get(position).activity));
            }
        });


        loading_container.showLoading();
        Observable.just(1)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        loading_container.showContent();
                    }
                });
    }


}
