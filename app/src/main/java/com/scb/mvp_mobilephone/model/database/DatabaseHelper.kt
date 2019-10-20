package com.scb.mvp_mobilephone.model.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.scb.mvp_mobilephone.model.MobileListResponse
import java.util.*

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "SCB_DB"
        private val DATABASE_VERSION = 1

        val TABLE_MOBILE: String = "mobiles"
        val COL_ID: String = "id"
        val COL_MOBILE_ID: String = "mobile_id"
        val COL_MOBILE_NAME: String = "name"
        val COL_MOBILE_BRAND: String = "brand"
        val COL_MOBILE_DESCRIPTION: String = "description"
        val COL_MOBILE_PRICE: String = "price"
        val COL_MOBILE_RATING: String = "rating"
        val COL_MOBILE_IMAGE_URL: String = "image_url"
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // Todo
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var sql = "CREATE TABLE $TABLE_MOBILE (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_MOBILE_ID INTEGER, " +
                "$COL_MOBILE_NAME TEXT, " +
                "$COL_MOBILE_BRAND TEXT, " +
                "$COL_MOBILE_DESCRIPTION TEXT, " +
                "$COL_MOBILE_PRICE DOUBLE DEFAULT 0, " +
                "$COL_MOBILE_RATING DOUBLE DEFAULT 0, " +
                "$COL_MOBILE_IMAGE_URL TEXT)"
        db?.execSQL(sql)
    }

    fun insertData(mobile: MobileListResponse): Boolean {
        val db: SQLiteDatabase = this.writableDatabase

        var cv = ContentValues()
        cv.put(COL_MOBILE_ID, mobile.id)
        cv.put(COL_MOBILE_NAME, mobile.name)
        cv.put(COL_MOBILE_BRAND, mobile.brand)
        cv.put(COL_MOBILE_DESCRIPTION, mobile.description)
        cv.put(COL_MOBILE_PRICE, mobile.price)
        cv.put(COL_MOBILE_RATING, mobile.rating)
        cv.put(COL_MOBILE_IMAGE_URL, mobile.thumbImageURL)
        return db.insert(TABLE_MOBILE, null, cv) > 0

    }

    fun getSelectPhone(name: String): Boolean {
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_MOBILE WHERE $COL_MOBILE_NAME = ?"
        db.rawQuery(selectQuery, arrayOf(name)).use {
            // .use requires API 16
            if (it.moveToFirst()) {
                return true
            }
        }
        return false
    }

    fun getAllFavorite(): List<MobileListResponse> {
        var lsFavorite: MutableList<MobileListResponse> = ArrayList()

        val db: SQLiteDatabase = this.readableDatabase
        val sql = "SELECT * FROM $TABLE_MOBILE"
        val cursor: Cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val mobile = MobileListResponse(
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getDouble(5),
                    cursor.getDouble(6),
                    cursor.getString(7)
                )
                lsFavorite.add(mobile)

            } while (cursor.moveToNext())
        }

        return lsFavorite
    }

    fun deleteAllData() {
        val db: SQLiteDatabase = this.writableDatabase
        val sql = "DELETE FROM $TABLE_MOBILE"

        db.execSQL(sql)

    }

    fun deleteData(mobileId: Int): Boolean {
        val db: SQLiteDatabase = this.writableDatabase

        return db.delete(TABLE_MOBILE, "$COL_MOBILE_ID = $mobileId", null) > 0

    }
}

