package com.scb.mvp_mobilephone.presenter

import com.scb.mvp_mobilephone.R
import com.scb.mvp_mobilephone.model.ApiManager
import com.scb.mvp_mobilephone.model.MobileApi
import com.scb.mvp_mobilephone.model.MobileListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileRepositoryImpl : MobileRepository {

    private val mobileApi: MobileApi = ApiManager.mobileApi

    override fun getList(cb: MobileCallback) {
        mobileApi.getAllMobiles()
            .enqueue(object : Callback<List<MobileListResponse>> {
                override fun onResponse(
                    call: Call<List<MobileListResponse>>,
                    response: Response<List<MobileListResponse>>
                ) {
                    val responseData = response.body()
                    if (responseData != null) {
                        cb.callbackSuccess(responseData)
                    } else {
                        cb.callbackFail(R.string.null_data)
                    }
                }
                override fun onFailure(call: Call<List<MobileListResponse>>, t: Throwable) {
                    cb.callbackFail(R.string.connection_error)
                }
            })
    }

}

interface MobileRepository {
    fun getList(cb: MobileCallback)
}

interface MobileCallback {
    fun callbackSuccess(responseData: List<MobileListResponse>)
    fun callbackFail(resId: Int)
}
