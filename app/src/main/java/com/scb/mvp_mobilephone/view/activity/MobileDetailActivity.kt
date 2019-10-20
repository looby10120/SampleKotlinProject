package com.scb.mvp_mobilephone.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephone.view.adapter.SlideImageAdapter
import com.scb.mvp_mobilephone.R
import com.scb.mvp_mobilephone.model.MobileImageResponse
import com.scb.mvp_mobilephone.model.MobileListResponse
import com.scb.mvp_mobilephone.presenter.DetailRepositoryImpl
import com.scb.mvp_mobilephone.presenter.MobileDetailContract
import com.scb.mvp_mobilephone.presenter.MobileDetailPresenter
import kotlinx.android.synthetic.main.activity_phone_detail.*

class MobileDetailActivity : AppCompatActivity(), MobileDetailContract.DetailView {

    companion object {
        fun startActivity(context: Context, intent: Intent) =
            context.startActivity(intent)
    }

    private val adapter = SlideImageAdapter(this, ArrayList())
    lateinit var presenter: MobileDetailContract.DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mobile = intent.getParcelableExtra<MobileListResponse>("data")

        presenter = MobileDetailPresenter(this, DetailRepositoryImpl())

        detail_viewpager.adapter = adapter

        presenter.getMobilePicture(mobile.id)
        presenter.getMobileDetail(mobile)

    }

    override fun showMobileImage(pictures: List<MobileImageResponse>) {
        adapter.setPicture(pictures)
    }

    override fun showMobileDetail(mobile: MobileListResponse) {
        mobile_name.text = mobile.name
        mobile_brand.text = mobile.brand
        mobile_description.text = mobile.description
    }

    override fun onSuccess(resId: Int) {
        Toast.makeText(applicationContext, getString(resId), Toast.LENGTH_LONG).show()
    }

    override fun onFail(resId: Int) {
        Toast.makeText(applicationContext, getString(resId), Toast.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
