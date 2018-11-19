package com.ihavenodomain.everysecondvalute.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrencyInfo {
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("rates")
    @Expose
    private Map<String, Double> rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public List<CurrencyRate> getRatesList() {
        if (rates == null) return null;

        List<CurrencyRate> cRates = new ArrayList<>(rates.size());

        for (Map.Entry<String, Double> entry : rates.entrySet()) {
            cRates.add(new CurrencyRate(entry.getKey(), entry.getValue()));
        }

        return cRates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
