package com.delizarov.coroutineusecase

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


class HoroscopeDto(
    val date: String,
    val horoscope: String,
    val sunsign: String
)

interface HoroscopeApi {

    @GET("/horoscope/today/{sunsign}")
    fun getHoroscope(@Path("sunsign") sunsign: String): Call<HoroscopeDto>
}