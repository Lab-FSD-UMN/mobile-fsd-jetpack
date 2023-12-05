package com.example.mobile_fsd_jetpack.api.response_model.room

import com.example.mobile_fsd_jetpack.models.Room

class GetRoomsApiResponse (
    val status : Int,
    val message : String,
    val data : List<Room>
)