package com.charry.xandroid.ui.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.charry.xandroid.MainActivity;
import com.charry.xandroid.R;

public class AnimDemoActivity extends AppCompatActivity {
    private Button btn_start;
    private Button btn_target;

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

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            }
        });

    }

    private void animFromXml() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        btn_target.startAnimation(anim);
    }

    private void animFromeCode() {
        TranslateAnimation ta = new TranslateAnimation(0, 100, 0, 100);
        ta.setDuration(1000);
        btn_target.startAnimation(ta);


        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
    }
}
