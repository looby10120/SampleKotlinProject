package com.scb.mvp_mobilephone.presenter

import com.scb.mvp_mobilephone.R
import com.scb.mvp_mobilephone.model.ApiManager
import com.scb.mvp_mobilephone.model.MobileApi
import com.scb.mvp_mobilephone.model.MobileImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRepositoryImpl : DetailRepository {

    private val mobileApi: MobileApi = ApiManager.mobileApi

    override fun getPicture(mobile_id: Int, cb: DetailCallback) {
        mobileApi.getImagePhone(mobile_id)
            .enqueue(object : Callback<List<MobileImageResponse>> {

                override fun onFailure(call: Call<List<MobileImageResponse>>, t: Throwable) {
                    cb.callbackFail(R.string.connection_error)
                }

                override fun onResponse(
                    call: Call<List<MobileImageResponse>>,
                    response: Response<List<MobileImageResponse>>
                ) {

                    val responseData = response.body()
                    if (responseData != null) {
                        cb.callbackSuccess(responseData)
                    } else {
                        cb.callbackFail(R.string.null_data)
                    }
                }

            })
    }

}

interface DetailRepository {
    fun getPicture(mobile_id: Int, cb: DetailCallback)
}

interface DetailCallback {
    fun callbackSuccess(responseData: List<MobileImageResponse>)
    fun callbackFail(resId: Int)
}
