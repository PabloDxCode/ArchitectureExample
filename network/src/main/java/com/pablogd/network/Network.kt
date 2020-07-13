package com.pablogd.network

import android.net.Network
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.net.CookieManager
import java.util.concurrent.TimeUnit

const val DUMMY_BASE_URL = "http://dummyurl.com"

private val TAG = Network::class.java.name
private val READ_TIME_OUT: Long = 60
private val CONECTION_TIME_OUT: Long = 60

fun createNetworkClient(cookies: CookieManager): Retrofit =
    retrofitClient(okHttpClient(), cookies)

private fun loggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
        Timber.tag(TAG).d(message)
    })
}

private fun okHttpClient(): OkHttpClient.Builder {
    val loggingInterceptor = loggingInterceptor()
    loggingInterceptor.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(CONECTION_TIME_OUT, TimeUnit.SECONDS)
}

private fun retrofitClient(okHttpClient: OkHttpClient.Builder, cookies: CookieManager): Retrofit {
    okHttpClient.cookieJar(JavaNetCookieJar(cookies))

    return Retrofit.Builder()
        .baseUrl(DUMMY_BASE_URL)
        .client(okHttpClient.build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
}