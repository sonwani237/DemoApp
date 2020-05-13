package com.demoapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            val interceptor = HttpLoggingInterceptor()
            retrofit = if (BuildConfig.DEBUG) {
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .build()
                Retrofit.Builder()
                    .baseUrl(Constant.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            } else {
                val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .build()
                Retrofit.Builder()
                    .baseUrl(Constant.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
        }
        return retrofit
    }
}