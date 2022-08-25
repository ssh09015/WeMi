package com.example.wemi.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("/v3/9904841e-293a-4f3d-ba73-9f7701118bc9")
    fun getHospitalData() : Call<HospitalDto> //응답받는 데이터 DTO
}