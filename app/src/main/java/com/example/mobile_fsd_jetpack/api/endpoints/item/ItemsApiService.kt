package com.example.mobile_fsd_jetpack.api.endpoints.item

import com.example.mobile_fsd_jetpack.api.response_model.item.GetItemsApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ItemsApiService {
    @GET("item")
    fun getItems(
    ) : Call<GetItemsApiResponse>
}