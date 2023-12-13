package com.example.mobile_fsd_jetpack.api.response_model.room

import com.example.mobile_fsd_jetpack.models.RoomReservationData

class GetSelfRoomReservationApiResponse (
    val status : Int,
    val message : String,
    val userReservation : List<RoomReservationData>
)