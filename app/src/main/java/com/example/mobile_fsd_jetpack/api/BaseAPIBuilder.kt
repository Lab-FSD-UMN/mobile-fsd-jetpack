package com.example.mobile_fsd_jetpack.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class BaseAPIBuilder {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://ba13-2001-448a-2042-3e13-a80d-7091-665e-de02.ngrok-free.app/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}