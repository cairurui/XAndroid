package com.charry.xandroid.http;



import com.charry.xandroid.BuildConfig;
import com.charry.xandroid.utils.Constent;
import com.charry.xandroid.utils.Xlog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static OkHttpClient mOkHttpClient = null;
    private static Retrofit mRetrofit;
    private static ApiService mApiService;

    public static ApiClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final  ApiClient INSTANCE = new ApiClient();
    }

    private ApiClient() {
        initOkHttpClient();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constent.BASE_URL)
                //设置 Json 转换器
                .addConverterFactory(GsonConverterFactory.create())
                //RxJava 适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory  .create())
                .client(mOkHttpClient)
                .build();


        mApiService = mRetrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return mApiService;
    }

    public OkHttpClient getOkhttpClient() {
        if (mOkHttpClient == null) {
            initOkHttpClient();
        }
        return mOkHttpClient;
    }

    private HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger(){
        @Override
        public void log(String message) {
            Xlog.d("OkHttp", message);
        }
    };


    private void initOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new NoNetworkInterceptor());

        if (BuildConfig.isDEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger);
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);


        mOkHttpClient = builder.build();
    }

}
