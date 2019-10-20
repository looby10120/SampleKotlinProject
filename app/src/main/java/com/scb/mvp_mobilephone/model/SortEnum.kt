package com.scb.mvp_mobilephone.model

enum class SortEnum constructor(private val stringValue: String, private val intValue: Int) {

    PRICE_LOW_TO_HIGH("Price low to high", 0),
    PRICE_HIGH_TO_LOW("Price high to low", 1),
    RATING_5_1("Rating 5-1", 2);

    fun getString(): String {
        return stringValue
    }

    fun getInt(): Int {
        return intValue
    }
}
