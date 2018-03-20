package com.charry.xandroid.ui.home.entity;

/**
 * Created by charry on 2018/1/31.
 */

public class HomeItemEntity {
    public String name;
    public Class activity;

    public HomeItemEntity(String name, Class activity) {
        this.name = name;
        this.activity = activity;
    }
}
