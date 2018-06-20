package com.charry.xandroid.ui.fixbug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.charry.xandroid.R;

public class FixBugRealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_bug_real);
    }

    public void click(View view) {

        Toast.makeText(this,"存在bug",Toast.LENGTH_SHORT).show();


    }
}
