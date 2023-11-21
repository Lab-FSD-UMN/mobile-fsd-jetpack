package com.example.mobile_fsd_jetpack.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class BaseAPIBuilder {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://f0e0-111-67-81-50.ngrok-free.app/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}