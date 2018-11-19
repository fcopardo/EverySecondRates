package com.ihavenodomain.everysecondvalute.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ihavenodomain.everysecondvalute.App;
import com.ihavenodomain.everysecondvalute.model.CurrencyInfo;
import com.ihavenodomain.everysecondvalute.model.CurrencyRepository;

public class MainViewModel extends ViewModel {
    private MutableLiveData<CurrencyInfo> currencyInfoData;
    private String baseCurrency = App.DEFAULT_CURRENCY;

    MutableLiveData<CurrencyInfo> getCurrencyInfo() {
        if (currencyInfoData == null) {
            currencyInfoData = new MutableLiveData<>();
        }

        startCurrencyLoadingSequence(baseCurrency);

        return currencyInfoData;
    }

    public void startCurrencyLoadingSequence(String baseCurrency) {
        if (baseCurrency == null) {
            baseCurrency = this.baseCurrency;
        } else {
            this.baseCurrency = baseCurrency;
        }
        CurrencyRepository.getInstance().getCurrencyInfo(baseCurrency, info -> currencyInfoData.setValue(info));
    }

    void endCurrencyLoadingSequence() {
        CurrencyRepository.getInstance().unbind();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        endCurrencyLoadingSequence();
    }
}
