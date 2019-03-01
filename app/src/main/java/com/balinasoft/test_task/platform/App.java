package com.balinasoft.test_task.platform;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.balinasoft.test_task.api.Api;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class App extends Application {

    static final String URL = "http://junior.balinasoft.com";

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static Retrofit retrofit;


    public static Context getContext() {
        return context;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build();
    }

}