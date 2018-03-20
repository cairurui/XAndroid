package com.charry.xandroid.http;


import com.charry.xandroid.ui.learningmvp.entity.LoginEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/doPost")
    Observable<LoginEntity>  doPost(@Query("name") String name,
                              @Query("email") String e
    );

    @GET("/doGet")
    Observable<LoginEntity> doGet(@Query("name") String name,
                                   @Query("age") int age
    );
}

