package com.charry.xandroid.ui.fixbug;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.charry.xandroid.R;
import com.charry.xandroid.utils.FixDexManager;

import java.io.File;

public class FixBugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_bug);

        fixDexBug();
    }

    public void fixBug(View view) {
        startActivity(new Intent(this, FixBugRealActivity.class));
    }

    private void fixDexBug() {
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");

        if (fixFile.exists()) {
            FixDexManager fixDexManager = new FixDexManager(this);
            try {
                fixDexManager.fixDex(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_LONG).show();
            }
        }
    }
}
