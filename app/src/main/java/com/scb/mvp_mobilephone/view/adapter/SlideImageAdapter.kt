package com.scb.mobilephone.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.scb.mvp_mobilephone.R
import com.scb.mvp_mobilephone.model.MobileImageResponse

class SlideImageAdapter(
    private val context: Context,
    private var mImageArray: ArrayList<MobileImageResponse>
) : PagerAdapter() {

    private var layoutInflater: LayoutInflater? = null

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return mImageArray.size
    }

    fun setPicture(mobiles: List<MobileImageResponse>) {
        this.mImageArray.clear()
        this.mImageArray.addAll(mobiles)
        this.notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.custom_image_layout, null)
        val image = view.findViewById<View>(R.id.image_view) as ImageView

        var imageUrl = mImageArray[position].url

        imageUrl = mapHttp(imageUrl)

        Glide.with(context)
            .load(imageUrl)
            .into(image)

        val viewPager = container as ViewPager
        viewPager.addView(view, 0)
        return view
    }

    private fun mapHttp(url: String): String {
        var imageUrl = url
        if (!imageUrl.contains("http://", ignoreCase = true) &&
            !imageUrl.contains("https://", ignoreCase = true)
        ) {

            imageUrl = "http://$imageUrl"
        }
        return imageUrl
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }
}
