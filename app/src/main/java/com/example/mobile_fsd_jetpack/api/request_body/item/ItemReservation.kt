package com.example.mobile_fsd_jetpack.api.request_body.item

import java.time.LocalDate
import java.time.LocalTime

class ItemReservation (
    val item_id : String,
    val quantity : Int,
    val reservation_date_start : String,
    val reservation_date_end : String,
    val reservation_time_start : String,
    val reservation_time_end : String,
    val note : String
)