package com.tripian.trpprovider.base

/**
 * Created by Semih Özköroğlu on 29.07.2018.
 */
abstract class AppConfig {

    // 2 dk
    val SESSION_TIMEOUT: Long = 120

    /**
     * Service urls
     */
    abstract fun serviceUrl(): String

    abstract fun apiKey(): String
}