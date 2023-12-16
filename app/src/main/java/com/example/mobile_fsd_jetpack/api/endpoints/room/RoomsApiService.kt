package com.example.mobile_fsd_jetpack.api.endpoints.room

import com.example.mobile_fsd_jetpack.api.request_body.item.ItemReservation
import com.example.mobile_fsd_jetpack.api.request_body.room.RoomReservation
import com.example.mobile_fsd_jetpack.api.response_model.ApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.item.GetSelfItemReservationApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomByIDApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomsApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.room.GetSelfRoomReservationApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RoomsApiService {
    @GET("room")
    fun getRooms(
    ) : Call<GetRoomsApiResponse>

    @GET("room/{id}")
    fun getRoomById(
        @Path("id") id : String?
    ) : Call<GetRoomByIDApiResponse>



    @POST("user/reservation/room")
    fun reserveRoom(
        @Header("Authorization") token: String,
        @Body requestBody: RoomReservation
    ) : Call<ApiResponse>

    @GET("user/reservation/room/myreservations")
    fun getSelfReservation(
        @Header("Authorization") token: String,
        @Header("Accept") contentType : String = "application/json"
    ) : Call<GetSelfRoomReservationApiResponse>
}