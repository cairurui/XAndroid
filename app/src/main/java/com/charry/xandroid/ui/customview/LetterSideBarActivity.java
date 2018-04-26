package com.charry.xandroid.ui.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.charry.xandroid.R;

public class LetterSideBarActivity extends AppCompatActivity {

    TextView tv;
    LetterSideBar lsb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_side_bar);

        tv = findViewById(R.id.tv);
        lsb = findViewById(R.id.lsb);


        lsb.setLetterListener(new LetterSideBar.OnLetterSelectListener() {
            @Override
            public void onLetterSelected(String letter) {
                tv.setText(letter);
            }
        });

    }
}
