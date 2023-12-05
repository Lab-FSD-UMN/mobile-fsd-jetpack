package com.example.mobile_fsd_jetpack.api.endpoints.item

import com.example.mobile_fsd_jetpack.api.response_model.item.GetRoomsApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface GetRoomsApiService {
    @GET("item")
    fun getRooms(
    ) : Call<GetRoomsApiResponse>
}