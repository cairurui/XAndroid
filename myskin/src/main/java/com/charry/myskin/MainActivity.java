package com.charry.myskin;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    OutletLoadingView outlet_view;

    TimePickerView time_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outlet_view = findViewById(R.id.outlet_view);

        time_picker = findViewById(R.id.time_picker);

        initView();
    }

    public void startAnim(View view) {

        outlet_view.startLoading();

        time_picker.setHour(10);
        time_picker.setMinute(20);

        int hour = time_picker.getHour();
        int minuter = time_picker.getMinute();
        Toast.makeText(this, hour+":" + minuter, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        final RightMarkView markView =
                (RightMarkView) findViewById(R.id.right_mark_rmv);
        // 设置开始和结束两种颜色
        markView.setColor(Color.parseColor("#FF4081"), Color.YELLOW);
        // 设置画笔粗细
        markView.setStrokeWidth(10f);

        Button bt = (Button) findViewById(R.id.bt_start);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markView.startAnimator();
            }
        });

    }

}
