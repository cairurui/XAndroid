package com.charry.xandroid.ui.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.charry.xandroid.R;

public class AnimDemoActivity extends AppCompatActivity {
    Button btn_start;
    Button btn_target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);
        btn_start = findViewById(R.id.btn_start);
        btn_target = findViewById(R.id.btn_target);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });
    }

    private void startAnim() {
//        animFromXml();
        animFromeCode();

    }

    private void animFromXml() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        btn_target.startAnimation(anim);
    }

    private void animFromeCode() {
        TranslateAnimation ta = new TranslateAnimation(0, 100, 0, 100);
        ta.setDuration(1000);
        btn_target.startAnimation(ta);
    }
}
