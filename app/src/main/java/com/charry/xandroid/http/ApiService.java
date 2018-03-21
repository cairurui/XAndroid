package com.charry.xandroid.http;


import com.charry.xandroid.ui.home.entity.BannerBean;
import com.charry.xandroid.ui.learningmvp.entity.LoginEntity;
import com.charry.xandroid.ui.signInUp.model.SignUpBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/doPost")
    Observable<LoginEntity> doPost(@Query("name") String name,
                                   @Query("email") String e
    );

    @POST("/user/register")
    Observable<SignUpBean> signUp(@Query("username") String name,
                                  @Query("password") String pwd,
                                  @Query("repassword") String repwd);

    @POST("/user/login")
    Observable<SignUpBean> signIn(@Query("username") String name,
                                  @Query("password") String pwd);

    @GET("/doGet")
    Observable<LoginEntity> doGet(@Query("name") String name,
                                  @Query("age") int age
    );

    @GET("/banner/json")
    Observable<BannerBean> getBanner();
}

