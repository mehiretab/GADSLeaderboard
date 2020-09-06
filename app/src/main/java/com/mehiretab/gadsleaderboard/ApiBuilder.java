package com.mehiretab.gadsleaderboard;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
    private static final String BASE_URL = "https://gadsapi.herokuapp.com/";
    private static ApiBuilder instance;
    private Retrofit retrofit;

    private ApiBuilder() {
        HttpLoggingInterceptor logger =
                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttp = new OkHttpClient.Builder().addInterceptor(logger);
        okHttp.connectTimeout(30, TimeUnit.SECONDS);
        okHttp.callTimeout(60, TimeUnit.SECONDS);
        okHttp.readTimeout(60, TimeUnit.SECONDS);
        okHttp.writeTimeout(60, TimeUnit.SECONDS);

        this.retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttp.build())
                .baseUrl(BASE_URL)
                .build();
    }

    public static synchronized ApiBuilder getInstance() {
        if (instance == null)
            instance = new ApiBuilder();
        return instance;
    }

    public <T> T getApi(Class<T> apiClass) {
        return this.retrofit.create(apiClass);
    }
}
