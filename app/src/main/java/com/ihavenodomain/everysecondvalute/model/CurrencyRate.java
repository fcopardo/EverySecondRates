package com.ihavenodomain.everysecondvalute.model;

import java.util.Objects;

public class CurrencyRate implements Cloneable {
    private String name;
    private Double value;
    private boolean isBase;

    public CurrencyRate() {
    }

    public CurrencyRate(String name, Double value) {
        this.name = name;
        this.value = value;
        this.isBase = false;
    }

    public CurrencyRate(String name, Double value, boolean isBase) {
        this.name = name;
        this.value = value;
        this.isBase = isBase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isBase() {
        return isBase;
    }

    public void setBase(boolean base) {
        isBase = base;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyRate)) return false;
        CurrencyRate that = (CurrencyRate) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
