package com.example.wemi.retrofit

import com.google.gson.annotations.SerializedName

data class HospitalDto(
    @SerializedName("items") val items : List<HospitalData>
)
