package com.example.mobile_fsd_jetpack.api

import com.example.mobile_fsd_jetpack.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class BaseAPIBuilder {
    val API_URL = BuildConfig.API_URL

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("${API_URL}/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}