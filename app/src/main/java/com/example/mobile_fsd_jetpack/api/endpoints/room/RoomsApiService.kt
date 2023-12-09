package com.example.mobile_fsd_jetpack.api.endpoints.room

import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomByIDApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomsApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomsApiService {
    @GET("room")
    fun getRooms(
    ) : Call<GetRoomsApiResponse>

    @GET("room/{id}")
    fun getRoomById(
        @Path("id") id : String?
    ) : Call<GetRoomByIDApiResponse>

}