package com.charry.xandroid.ui.learningmvp.entity;

/**
 * Created by charry on 2018/1/30.
 */

public class LoginEntity {

    public String name;
    public int age;
    public String email;

    @Override
    public String toString() {
        return "LoginEntity{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
