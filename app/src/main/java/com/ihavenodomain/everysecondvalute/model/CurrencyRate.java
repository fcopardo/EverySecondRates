package com.ihavenodomain.everysecondvalute.model;

import java.util.Objects;

public class CurrencyRate implements Cloneable {
    private String name;
    private Double value;

    public CurrencyRate() {
    }

    public CurrencyRate(String name, Double value) {
        this.name = name;
        this.value = value;
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
