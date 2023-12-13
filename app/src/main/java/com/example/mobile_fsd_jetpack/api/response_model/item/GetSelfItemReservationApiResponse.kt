package com.example.mobile_fsd_jetpack.api.response_model.item

import com.example.mobile_fsd_jetpack.models.ItemReservationData

class GetSelfItemReservationApiResponse (
    val status : Int,
    val message : String,
    val userReservation : List<ItemReservationData>
)