package com.example.mobile_fsd_jetpack.api.response_model.item

import com.example.mobile_fsd_jetpack.models.Item

class GetItemByIDApiResponse (
    val status : Int,
    val message : String,
    val data : Item
)