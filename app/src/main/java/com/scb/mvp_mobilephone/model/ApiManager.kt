package com.scb.mvp_mobilephone.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    val mobileApi by lazy { createService<MobileApi>("https://scb-test-mobile.herokuapp.com/api/") }

    private inline fun <reified T> createService(baseUrl: String): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .run { create(T::class.java) }
}
