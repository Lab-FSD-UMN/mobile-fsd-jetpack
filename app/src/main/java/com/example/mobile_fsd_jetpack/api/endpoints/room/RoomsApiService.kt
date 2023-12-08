package com.example.mobile_fsd_jetpack.api.endpoints.room

import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomsApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface RoomsApiService {
    @GET("room")
    fun getRooms(
    ) : Call<GetRoomsApiResponse>
}