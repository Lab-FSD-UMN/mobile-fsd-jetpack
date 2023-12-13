package com.example.mobile_fsd_jetpack.models

class ItemReservationData (
    val id : String,
    val item : Item,
    val quantity : Int,
    val status : Int,
    val reservation_start_time : String,
    val reservation_end_time : String,
    val note : String,
    val created_at : String,
    val updated_at : String
)