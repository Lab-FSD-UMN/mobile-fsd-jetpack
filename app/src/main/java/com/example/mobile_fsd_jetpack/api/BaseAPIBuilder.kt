package com.example.mobile_fsd_jetpack.api

import com.example.mobile_fsd_jetpack.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()
class BaseAPIBuilder {
    val API_URL = BuildConfig.API_URL

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("${API_URL}/api/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}