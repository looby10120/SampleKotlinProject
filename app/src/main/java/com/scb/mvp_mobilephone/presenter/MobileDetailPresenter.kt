package com.scb.mvp_mobilephone.presenter

import com.scb.mvp_mobilephone.R
import com.scb.mvp_mobilephone.model.MobileImageResponse
import com.scb.mvp_mobilephone.model.MobileListResponse

class MobileDetailPresenter(

    private val view: MobileDetailContract.DetailView,
    private val repository: DetailRepository

) : MobileDetailContract.DetailPresenter {

    override fun getMobileDetail(mobile: MobileListResponse) {
        view.showMobileDetail(mobile)
    }

    override fun getMobilePicture(mobileId: Int) {
        repository.getPicture(mobileId , object : DetailCallback {
            override fun callbackSuccess(responseData: List<MobileImageResponse>) {
                view.showMobileImage(responseData)
                view.onSuccess(R.string.image_success)
            }

            override fun callbackFail(resId: Int) {
                view.onFail(resId)
            }
        })
    }

}
