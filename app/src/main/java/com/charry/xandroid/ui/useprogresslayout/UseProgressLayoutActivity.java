package com.charry.xandroid.ui.useprogresslayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.widget.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

public class UseProgressLayoutActivity extends BaseActivity {

    ProgressLayout progressLayout;

    @Override
    public Object getContentView() {
        return R.layout.activity_use_progress_layout ;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setUpTitle("Progress Activity");
        progressLayout = findViewById(R.id.progress);


        Drawable emptyDrawable = getResources().getDrawable(R.drawable.icon_emptu);


        Drawable errorDrawable = getResources().getDrawable(R.drawable.icon_no_connection);

        //Add which views you don't want to hide. In this case don't hide the toolbar
        List<Integer> skipIds = new ArrayList<>();
        skipIds.add(R.id.toolbar);

        String state = getIntent().getStringExtra("STATE");
        switch (state) {
            case "LOADING":
                progressLayout.showLoading(skipIds);
                setTitle("Loading");
                break;
            case "EMPTY":
                progressLayout.showEmpty(emptyDrawable,
                        "Empty Shopping Cart",
                        "Please add things in the cart to continue.", skipIds);
                setTitle("Empty");
                break;
            case "ERROR":
                progressLayout.showError(errorDrawable,
                        "No Connection",
                        "We could not establish a connection with our servers. Please try again when you are connected to the internet.",
                        "Try Again", errorClickListener, skipIds);
                setTitle("Error");
                break;
            case "CONTENT":
                progressLayout.showContent();
                setTitle("Content");
                break;
        }
    }

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplication(), "Try again button clicked", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
