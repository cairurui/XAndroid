package com.charry.xandroid.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseFragment;
import com.charry.xandroid.ui.Learningfragment.LearningFragmentActivity;
import com.charry.xandroid.ui.home.mvp.HomePagePresenter;
import com.charry.xandroid.ui.home.mvp.HomePageView;
import com.charry.xandroid.ui.home.adapter.BannerViewHolder;
import com.charry.xandroid.ui.home.adapter.HomeFragmentAdapter;
import com.charry.xandroid.ui.home.entity.BannerBean;
import com.charry.xandroid.ui.home.entity.HomeItemEntity;
import com.charry.xandroid.ui.learningAndfix.LearningAndfixActivity;
import com.charry.xandroid.ui.learningConstraintLayout.LearningConstraintLayoutActivity;
import com.charry.xandroid.ui.learningHandler.LearningHandlerActivity;
import com.charry.xandroid.ui.learningHeart.learningHeartActivity;
import com.charry.xandroid.ui.learningSwipeBackActivity.SB1_SwipeBackActivity;
import com.charry.xandroid.ui.learningWebview.learningWebviewActivity;
import com.charry.xandroid.ui.learningmvp.LearningMVPActivity;
import com.charry.xandroid.ui.useprogresslayout.UseProgressLayoutListActivity;
import com.charry.xandroid.utils.SizeUtils;
import com.charry.xandroid.widget.progresslayout.ProgressLayout;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends BaseFragment<HomePagePresenter> implements HomePageView {

    @BindView(R.id.rv_home)
    RecyclerView rv_home;

    @BindView(R.id.loading_container)
    ProgressLayout loading_container;

    List<HomeItemEntity> mList;
    private HomeFragmentAdapter mHomeFragmentAdapter;
    private MZBannerView mMZBanner;
    private List mBannerList;

    {
        mList = new ArrayList<>();
        mList.add(new HomeItemEntity("fragment切换", LearningFragmentActivity.class));
        mList.add(new HomeItemEntity("mvp使用", LearningMVPActivity.class));
        mList.add(new HomeItemEntity("状态图的使用", UseProgressLayoutListActivity.class));
        mList.add(new HomeItemEntity("swipe_back activity 学习", SB1_SwipeBackActivity.class));
        mList.add(new HomeItemEntity("学习 constrainlayout", LearningConstraintLayoutActivity.class));
        mList.add(new HomeItemEntity("学习 webview", learningWebviewActivity.class));
        mList.add(new HomeItemEntity("学习 心跳包机制", learningHeartActivity.class));
        mList.add(new HomeItemEntity("学习 handle 机制", LearningHandlerActivity.class));
        mList.add(new HomeItemEntity("学习 andfix", LearningAndfixActivity.class));
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
    protected HomePagePresenter createPresenter() {
        return new HomePagePresenter();
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

        mBannerList = new ArrayList<BannerBean>();
        mMZBanner = (MZBannerView) View.inflate(getContext(), R.layout.banner_home, null);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(200));
        mMZBanner.setLayoutParams(layoutParams);

        initBanner();
        mHomeFragmentAdapter.addHeaderView(mMZBanner);
        mHomeFragmentAdapter.notifyDataSetChanged();

        getPresenter().getBanner();
    }

    private void initBanner() {
        mBannerList.add(new BannerBean.DataBean());

        // 设置数据
        mMZBanner.setPages(mBannerList, mMZHolderCreator);
    }

    MZHolderCreator mMZHolderCreator =   new MZHolderCreator<BannerViewHolder>() {
        @Override
        public BannerViewHolder createViewHolder() {
            return new BannerViewHolder();
        }
    };

    @Override
    public void onBannerViewSuc(List<BannerBean.DataBean> data) {
        mMZBanner.setPages(mBannerList, mMZHolderCreator);
        mHomeFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBannerFail(String errorMsg) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();//开始轮播
    }

}
