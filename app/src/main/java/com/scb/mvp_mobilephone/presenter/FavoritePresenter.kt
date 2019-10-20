package com.scb.mvp_mobilephone.presenter

import com.scb.mvp_mobilephone.model.MobileListResponse
import com.scb.mvp_mobilephone.model.SortEnum
import com.scb.mvp_mobilephone.model.database.DatabaseHelper

class FavoritePresenter(

    private val view: FavoriteContract.FavoriteView,
    private val databaseHelper: DatabaseHelper
)

    : FavoriteContract.FavoritePresenter {

    private lateinit var favoriteList: List<MobileListResponse>
    private lateinit var favoritesSorted: List<MobileListResponse>
    private lateinit var databaseData: List<MobileListResponse>
    private var deleteMobileId = 0

    override fun getAllFavoriteList() {
        favoriteList = databaseHelper.getAllFavorite()
        view.showAllFavoriteList(favoriteList)
    }

    override fun clearData() {
        view.clearFavoriteList()
    }

    override fun deleteFavorite(position: Int) {

        val dataList = databaseHelper.getAllFavorite() as MutableList
        this.deleteMobileId = dataList[position].id
        databaseHelper.deleteAllData()
        dataList.removeAt(position)
        dataList.forEach { databaseHelper.insertData(it) }
        view.swipeToDelete(dataList)

    }

    override fun sendDataToActivity(updatable: Updatable) {
        updatable.updateData(this.deleteMobileId)
    }

    override fun onFavoriteItemClicked(mobile: MobileListResponse) {
        view.showDetail(mobile)
    }

    override fun sortData(sortType: SortEnum) {

        databaseData = databaseHelper.getAllFavorite()
        databaseHelper.deleteAllData()

        when (sortType.getInt()) {
            0 -> {
                favoritesSorted = databaseData.sortedWith(compareBy { it.price })
                for (mobile in favoritesSorted) {
                    databaseHelper.insertData(mobile)
                }
            }
            1 -> {
                favoritesSorted = databaseData.sortedWith(compareBy { it.price }).reversed()
                for (mobile in favoritesSorted) {
                    databaseHelper.insertData(mobile)
                }
            }
            2 -> {
                favoritesSorted = databaseData.sortedWith(compareBy { it.rating }).reversed()
                for (mobile in favoritesSorted) {
                    databaseHelper.insertData(mobile)
                }
            }
        }
        view.showAllFavoriteList(favoritesSorted)
    }

}
