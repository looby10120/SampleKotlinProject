package com.scb.mvp_mobilephone.presenter

import com.scb.mvp_mobilephone.model.MobileListResponse
import com.scb.mvp_mobilephone.model.SortEnum

interface FavoriteContract {

    interface FavoriteView {

        fun showAllFavoriteList(mobiles: List<MobileListResponse>)

        fun clearFavoriteList()

        fun showDetail(mobile: MobileListResponse)

        fun swipeToDelete(mobiles: List<MobileListResponse>)

    }

    interface FavoritePresenter {

        fun getAllFavoriteList()

        fun deleteFavorite(position: Int)

        fun sendDataToActivity(updatable: Updatable)

        fun clearData()

        fun onFavoriteItemClicked(mobile: MobileListResponse)

        fun sortData(sortType: SortEnum)

    }
}
