package com.scb.mvp_mobilephone.presenter

import com.scb.mvp_mobilephone.model.MobileImageResponse
import com.scb.mvp_mobilephone.model.MobileListResponse

interface MobileDetailContract {

    interface DetailView {

        fun showMobileImage(pictures: List<MobileImageResponse>)

        fun showMobileDetail(mobile: MobileListResponse)

        fun onSuccess(resId: Int)

        fun onFail(resId: Int)

    }

    interface DetailPresenter {

        fun getMobilePicture(mobileId: Int)

        fun getMobileDetail(mobile : MobileListResponse)

    }
}
