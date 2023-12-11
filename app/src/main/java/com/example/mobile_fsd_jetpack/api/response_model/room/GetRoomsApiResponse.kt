package com.example.mobile_fsd_jetpack.api.response_model.room

import com.example.mobile_fsd_jetpack.models.Room

data class GetRoomsApiResponse(
    val code: Int,
    val message: String,
    val data: RoomApiData
)

data class RoomApiData(
    val rooms: List<Room>,
    val current_page: Int,
    val total: Int,
    val per_page: Int,
    val last_page: Int,
    val next_page_url: String?,
    val prev_page_url: String?
)