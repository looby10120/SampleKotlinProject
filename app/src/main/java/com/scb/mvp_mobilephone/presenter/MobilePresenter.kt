package com.scb.mvp_mobilephone.presenter

import com.scb.mvp_mobilephone.R
import com.scb.mvp_mobilephone.model.MobileListResponse
import com.scb.mvp_mobilephone.model.SortEnum
import com.scb.mvp_mobilephone.model.database.DatabaseHelper

class MobilePresenter(

    private val view: MobileListContract.MobileView,
    private val repository: MobileRepository,
    private val databaseHelper: DatabaseHelper
)

    : MobileListContract.MobilePresenter {

    private lateinit var mobiles: List<MobileListResponse>
    private lateinit var mobilesSorted: List<MobileListResponse>

    override fun getAllMobilesList() {
        databaseHelper.deleteAllData()
        repository.getList(object : MobileCallback {
            override fun callbackSuccess(responseData: List<MobileListResponse>) {
                mobiles = responseData
                view.showAllMobilesList(responseData)
                view.onSuccess(R.string.success)
            }

            override fun callbackFail(resId: Int) {
                view.onFail(resId)
            }

        })
    }

    override fun updateFavoriteButton(mobileId: Int) {
        this.mobiles.forEach {
            if(it.id == mobileId && it.isFav){
                it.isFav = false
            }
        }
        view.showAllMobilesList(this.mobiles)
    }

    override fun onMobileItemClicked(mobile: MobileListResponse) {
        view.showDetail(mobile)
    }

    override fun onFavoriteClicked(mobile: MobileListResponse) {
        if (databaseHelper.getSelectPhone(mobile.name)) {
            databaseHelper.deleteData(mobile.id)
            mobile.isFav = false
        } else {
            databaseHelper.insertData(mobile)
            mobile.isFav = true
        }
    }

    override fun sortData(sortType: SortEnum) {

        when (sortType.getInt()) {
            0 -> {
                mobilesSorted = mobiles.sortedWith(compareBy { it.price })
            }
            1 -> {
                mobilesSorted = mobiles.sortedWith(compareBy { it.price }).reversed()
            }
            2 -> {
                mobilesSorted = mobiles.sortedWith(compareBy { it.rating }).reversed()
            }
        }
        view.showAllMobilesList(mobilesSorted)
    }

}
