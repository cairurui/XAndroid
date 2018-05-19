package com.xiaocai.messageclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide


class DemoGlideActivity : AppCompatActivity() {
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_glide)

        imageView = findViewById(R.id.image_view)
    }

    fun loadImage(v: View) {
        val url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg"

        Glide.with(this).load(url).into(imageView)

    }
}
