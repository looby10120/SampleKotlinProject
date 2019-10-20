package com.scb.mvp_mobilephone.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MobileApi {
    @GET("mobiles/")
    fun getAllMobiles(): Call<List<MobileListResponse>>

    @GET("mobiles/{id}/images/")
    fun getImagePhone(@Path("id") id: Int): Call<List<MobileImageResponse>>
}
