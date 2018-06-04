package com.charry.xandroid.ui.customview.drag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.charry.xandroid.MainActivity;
import com.charry.xandroid.R;
import com.charry.xandroid.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class DragDemoActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_demo);
        StatusBarUtil.setStatusBarBg(this, Color.RED);

        mListView = (ListView) findViewById(R.id.list_view);

        mItems = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            mItems.add("i -> " + i);
        }

        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView item = (TextView) LayoutInflater.from(DragDemoActivity.this)
                        .inflate(R.layout.item_lv, parent, false);
                item.setText(mItems.get(position));
                return item;
            }
        });
    }
}
