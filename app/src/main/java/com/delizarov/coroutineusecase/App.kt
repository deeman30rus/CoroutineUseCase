package com.delizarov.coroutineusecase

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val HOST = "http://horoscope-api.herokuapp.com/"

class App : Application() {

    init {
        INSTANCE = this
    }

    val horoscopeApi: HoroscopeApi by lazy { initApi() }


    private fun initApi() : HoroscopeApi {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(HOST)
            .build()


        return retrofit.create<HoroscopeApi>(HoroscopeApi::class.java)
    }

    companion object {

        lateinit var INSTANCE: App
    }
}