package com.example.mobile_fsd_jetpack.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class BaseAPIBuilder {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://d3fc-2001-448a-2042-3e13-f98f-5b8d-b2b4-c598.ngrok-free.app/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}