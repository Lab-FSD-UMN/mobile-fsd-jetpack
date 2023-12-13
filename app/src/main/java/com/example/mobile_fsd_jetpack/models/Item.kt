package com.example.mobile_fsd_jetpack.models

class Item (
    val id : String,
    val image : String,
    val name : String,
    val quantity : Int,
    val reserved_qty : Int,
    val is_available : Boolean,
    val description : String,
    val created_at : String,
    val updated_at : String
)