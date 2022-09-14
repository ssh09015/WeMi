package com.example.wemi.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/v3/eae2dbd9-5eee-4c54-b7eb-0df7ebd611d0") // mocky url
    fun getHospitalData() : Call<HospitalDto> //응답받는 데이터 DTO
}
