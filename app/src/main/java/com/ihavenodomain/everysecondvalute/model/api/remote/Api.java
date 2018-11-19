package com.ihavenodomain.everysecondvalute.model.api.remote;

import com.ihavenodomain.everysecondvalute.model.CurrencyInfo;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("latest")
    Flowable<CurrencyInfo> getCurrencyInfo(@Query("base") String base);
}
