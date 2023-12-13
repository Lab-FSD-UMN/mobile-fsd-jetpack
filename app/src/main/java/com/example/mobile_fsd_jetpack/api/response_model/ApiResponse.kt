package com.example.mobile_fsd_jetpack.api.response_model

import com.google.gson.annotations.SerializedName

class ApiResponse (
    @SerializedName("status") val status : Int?,
    @SerializedName("message") val message : String?,
)
