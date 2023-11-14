package com.example.mobile_fsd_jetpack.api.response_model

import com.example.mobile_fsd_jetpack.models.User

class LoginApiResponse (
    val message : String,
    val token : String,
    val user : User
)