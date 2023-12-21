package com.example.mobile_fsd_jetpack.api.endpoints

import com.example.mobile_fsd_jetpack.api.response_model.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface FCM {
    @POST("auth/fcm/token")
    fun saveFCMToken(
        @Header("Authorization") token: String,
        @Body requestBody: FCMTokenRequest
    ): Call<ApiResponse>
}

data class FCMTokenRequest(val fcm_token: String)