package com.charry.xandroid.ui.learningAndfix;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.charry.xandroid.MyApplication;
import com.charry.xandroid.R;
import com.charry.xandroid.utils.Xlog;

import java.io.IOException;

public class LearningAndfixActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_andfix);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int a = 2 / 1;
        Toast.makeText(this, "结果:" + a, Toast.LENGTH_LONG).show();
    }

}
