package com.example.wemi.retrofit

import com.google.gson.annotations.SerializedName

data class HospitalData(
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("price") val price : String,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lng") val lng : Double,
    @SerializedName("imgUrl") val imgUrl : String,
)
