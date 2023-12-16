package com.example.mobile_fsd_jetpack.api.request_body.room

class RoomReservation (
    val room_id : String,
    val reservation_date_start : String,
    val reservation_date_end : String,
    val reservation_time_start : String,
    val reservation_time_end : String,
    val note : String
)
