package com.scb.mvp_mobilephone.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MobileListResponse(
    val brand: String,
    val description: String,
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Double,
    val thumbImageURL: String,
    var isFav: Boolean = false
) : Parcelable
