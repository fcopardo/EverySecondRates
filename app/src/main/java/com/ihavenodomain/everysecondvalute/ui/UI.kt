package com.ihavenodomain.everysecondvalute.ui

interface UI<T> {
    fun setData(data: T)
    fun getData() : T?
}