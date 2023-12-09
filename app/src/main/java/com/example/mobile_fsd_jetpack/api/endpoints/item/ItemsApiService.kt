package com.example.mobile_fsd_jetpack.api.endpoints.item

import com.example.mobile_fsd_jetpack.api.response_model.item.GetItemByIDApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.item.GetItemsApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomByIDApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemsApiService {
    @GET("item")
    fun getItems(
    ) : Call<GetItemsApiResponse>

    @GET("item/{id}")
    fun getItemById(
        @Path("id") id : String?
    ) : Call<GetItemByIDApiResponse>
}