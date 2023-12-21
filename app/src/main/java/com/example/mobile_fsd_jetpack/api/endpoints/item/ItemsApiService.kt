package com.example.mobile_fsd_jetpack.api.endpoints.item

import com.example.mobile_fsd_jetpack.api.request_body.item.ItemReservation
import com.example.mobile_fsd_jetpack.auth.UserAuth
import com.example.mobile_fsd_jetpack.api.response_model.ApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.item.GetItemByIDApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.item.GetItemsApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.item.GetSelfItemReservationApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomByIDApiResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ItemsApiService {
    @GET("item")
    fun getItems(
    ) : Call<GetItemsApiResponse>

    @GET("item/{id}")
    fun getItemById(
        @Path("id") id : String?
    ) : Call<GetItemByIDApiResponse>

    @POST("user/reservation/item")
    fun reserveItem(
        @Header("Authorization") token: String,
        @Body requestBody: ItemReservation  
    ) : Call<ApiResponse>

    @GET("user/reservation/request")
    fun getSelfReservation(
        @Header("Authorization") token: String,
        @Header("Accept") contentType : String = "application/json"
    ) : Call<GetSelfItemReservationApiResponse>
}