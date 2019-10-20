package com.scb.mvp_mobilephone.presenter

import com.scb.mvp_mobilephone.model.MobileListResponse
import com.scb.mvp_mobilephone.model.SortEnum

interface MobileListContract {

    interface MobileView {

        fun showAllMobilesList(mobiles: List<MobileListResponse>)

        fun showDetail(mobile: MobileListResponse)

        fun onSuccess(resId: Int)

        fun onFail(resId: Int)

    }

    interface MobilePresenter {

        fun getAllMobilesList()

        fun updateFavoriteButton(mobileId: Int)

        fun onMobileItemClicked(mobile: MobileListResponse)

        fun onFavoriteClicked(mobile: MobileListResponse)

        fun sortData(sortType: SortEnum)

    }
}
