package com.ihavenodomain.everysecondvalute.model;

import com.ihavenodomain.everysecondvalute.model.api.remote.ApiConnection;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CurrencyRepository {
    private static final CurrencyRepository ourInstance = new CurrencyRepository();

    public static CurrencyRepository getInstance() {
        return ourInstance;
    }

    public CurrencyRepository() {
    }

    private Disposable currencyInfoDisposable;

    public void getCurrencyInfo(String baseCurrency, ICallback callback) {
        unbind();

        currencyInfoDisposable = Flowable.interval(0,1, TimeUnit.SECONDS, Schedulers.single())
                .flatMap(tick -> ApiConnection.getInstance().getApi().getCurrencyInfo(baseCurrency))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(err -> {
                    // some kind of local storage reading may be used here
                    callback.currencyLoaded(null);
                })
                .retry()
                .subscribe(currencyInfo -> {
                    // local storage writing may be used here
                    callback.currencyLoaded(currencyInfo);
                    }, error -> {}
                );
    }

    public void unbind() {
        if (currencyInfoDisposable == null) return;
        if (!currencyInfoDisposable.isDisposed()) currencyInfoDisposable.dispose();
    }

    public interface ICallback {
        void currencyLoaded(CurrencyInfo info);
    }
}
