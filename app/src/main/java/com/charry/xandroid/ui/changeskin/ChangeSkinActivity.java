package com.charry.xandroid.ui.changeskin;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.charry.xandroid.R;
import com.charry.xandroid.utils.Xlog;

import java.io.File;
import java.lang.reflect.Method;

public class ChangeSkinActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_skin);
        iv = findViewById(R.id.iv);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage();
            }
        });
    }

    private void changeImage() {
        try {
            // 文件路径 eg: /storage/emulated/0/myskin.skin  这个 myskin.skin 其实是 apk 文件改了下后缀名
            String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "myskin.skin";
            File file = new File(path);
            if (!file.exists()) {
                Toast.makeText(this, "资源文件包不存在", Toast.LENGTH_SHORT).show();
                return;
            }

            // 反射创建 AssetManager 并调用 addAssertPath 方法
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);

            // 创建 Resources
            Resources resourcesSuper = getResources();
            Resources resources = new Resources(assetManager, resourcesSuper.getDisplayMetrics(), resourcesSuper.getConfiguration());

            // 获取资源 ID  1.资源名称（图片名） 2.资源类型（drawable） 3.包名(不一定是当前应用的包名，是生成资源的apk的包名)
            int drawableId = resources.getIdentifier("image", "drawable", "com.charry.myskin");
            Drawable drawable = resources.getDrawable(drawableId);
            iv.setImageDrawable(drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
