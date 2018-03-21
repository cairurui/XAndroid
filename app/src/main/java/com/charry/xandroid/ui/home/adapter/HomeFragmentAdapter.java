package com.charry.xandroid.ui.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.charry.xandroid.R;
import com.charry.xandroid.ui.home.entity.HomeItemEntity;

import java.util.List;

/**
 * Created by charry on 2018/1/31.
 */

public class HomeFragmentAdapter extends BaseQuickAdapter<HomeItemEntity, BaseViewHolder> {

    public HomeFragmentAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    private int[] colors = {R.color.color_ff33b5e5, R.color.color_ff33b5e5};



    @Override
    protected void convert(BaseViewHolder helper, HomeItemEntity item) {
        helper.setText(R.id.tv_name, item.name);
        helper.addOnClickListener(R.id.tv_name);
    }
}

