package com.tripian.trpprovider.util

/**
 * Created by semihozkoroglu on 9.08.2020.
 */
interface Callback<T> {
    fun onResponse(data: T?)
}