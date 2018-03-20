package com.charry.xandroid.ui.useprogresslayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.base.BasePresenter;

public class UseProgressLayoutListActivity extends BaseActivity {


    @Override
    protected void initView(Bundle savedInstanceState) {
        setUpTitle("Progress Activity");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public Object getContentView() {
        return R.layout.activity_use_progress_layout_list;
    }

    public void onLoadingStateClick(View view) {
        Intent intent = new Intent(getApplicationContext(), UseProgressLayoutActivity.class);
        intent.putExtra("STATE", "LOADING");
        startActivity(intent);
    }

    public void onEmptyStateClick(View view) {
        Intent intent = new Intent(getApplicationContext(), UseProgressLayoutActivity.class);
        intent.putExtra("STATE", "EMPTY");
        startActivity(intent);
    }

    public void onErrorStateClick(View view) {
        Intent intent = new Intent(getApplicationContext(), UseProgressLayoutActivity.class);
        intent.putExtra("STATE", "ERROR");
        startActivity(intent);
    }

    public void onContentStateClick(View view) {
        Intent intent = new Intent(getApplicationContext(), UseProgressLayoutActivity.class);
        intent.putExtra("STATE", "CONTENT");
        startActivity(intent);
    }
}
