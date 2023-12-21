package com.example.mobile_fsd_jetpack.api.endpoints

import com.example.mobile_fsd_jetpack.api.response_model.LoginApiResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
interface LoginApiService {
    @POST("auth/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginApiResponse>
}