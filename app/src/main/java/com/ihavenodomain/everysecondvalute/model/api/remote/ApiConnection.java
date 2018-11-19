package com.ihavenodomain.everysecondvalute.model.api.remote;

import com.ihavenodomain.everysecondvalute.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConnection {
    private static final ApiConnection ourInstance = new ApiConnection();

    public static ApiConnection getInstance() {
        return ourInstance;
    }

    private ApiConnection() {
    }

    public Api getApi() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BuildConfig.apiEndpoint)
                .build();
        return retrofit.create(Api.class);
    }
}
