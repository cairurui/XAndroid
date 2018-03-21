package com.charry.xandroid.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.request.RequestOptions;
import com.charry.xandroid.R;
import com.charry.xandroid.ui.home.entity.BannerBean;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * Created by charry on 2018/3/21.
 */

public class BannerViewHolder implements MZViewHolder<BannerBean.DataBean> {

    private ImageView mImageView;
    RequestOptions requestOptions = new RequestOptions();


    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item_home, null);
        mImageView = (ImageView) view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, BannerBean.DataBean data) {
        // 数据绑定
        requestOptions.placeholder(R.drawable.icon_emptu);
        requestOptions.error(R.drawable.icon_emptu);

        Glide.with(context)
                .load(data.imagePath)
                .apply(requestOptions)
                .into(mImageView);
    }
}