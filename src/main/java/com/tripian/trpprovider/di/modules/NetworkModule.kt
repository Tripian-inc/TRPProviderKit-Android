package com.tripian.trpprovider.di.modules

import android.util.Log
import com.google.gson.Gson
import com.tripian.trpprovider.BuildConfig
import com.tripian.trpprovider.base.AppConfig
import com.tripian.trpprovider.repository.services.Service
import dagger.Module
import dagger.Provides
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Semih Özköroğlu on 29.07.2018.
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideOkHttp(appConfig: AppConfig): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.e("OkHttp", it)
        })

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        builder.addInterceptor { chain ->
            val originalRequest = chain.request()

            val newRequestBuilder = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer ${appConfig.apiKey()}")
                    .cacheControl(CacheControl.FORCE_NETWORK)

            return@addInterceptor chain.proceed(newRequestBuilder.build())
        }

        builder.addInterceptor(interceptor)
        builder.connectTimeout(appConfig.SESSION_TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(appConfig.SESSION_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(appConfig.SESSION_TIMEOUT, TimeUnit.SECONDS)

        builder.hostnameVerifier { _, _ -> true }

        return builder.build()
    }

    @Provides
    @Singleton
    internal fun provideService(appConfig: AppConfig, okHttpClient: OkHttpClient, gson: Gson): Service {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(appConfig.serviceUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()

        return retrofit.create(Service::class.java)
    }
}