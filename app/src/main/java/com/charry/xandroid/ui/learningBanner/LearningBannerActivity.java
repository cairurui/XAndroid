package com.charry.xandroid.ui.learningBanner;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.charry.xandroid.R;
import com.charry.xandroid.ui.home.adapter.BannerViewHolder;
import com.charry.xandroid.ui.home.entity.BannerBean;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.List;

public class LearningBannerActivity extends AppCompatActivity {
    private MZBannerView mMZBanner;
    private List mBannerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_banner);

        mMZBanner = (MZBannerView)   findViewById(R.id.my_banner);

        initBanner();
    }

    private void setBanner(List<Movie> movies){
        mMZBanner.setPages(movies, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        mMZBanner.start();

    }


    private void initBanner() {
        mBannerList = new ArrayList<BannerBean>();
        mBannerList.add(new BannerBean.DataBean( R.drawable.banner1));
        mBannerList.add(new BannerBean.DataBean( R.drawable.banner2));
        mBannerList.add(new BannerBean.DataBean( R.drawable.banner3));
        mBannerList.add(new BannerBean.DataBean( R.drawable.banner4));
        mBannerList.add(new BannerBean.DataBean( R.drawable.banner5));

        // 设置数据
        mMZBanner.setPages(mBannerList, mMZHolderCreator);
    }

    MZHolderCreator mMZHolderCreator =   new MZHolderCreator<BannerViewHolder>() {
        @Override
        public BannerViewHolder createViewHolder() {
            return new BannerViewHolder();
        }
    };


    public void onBannerViewSuc(List<BannerBean.DataBean> data) {
        mBannerList.clear();
        mBannerList.addAll(data);
        mMZBanner.setPages(mBannerList, mMZHolderCreator);
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
