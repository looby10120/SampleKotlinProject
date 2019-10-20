package com.scb.mvp_mobilephone.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.scb.mvp_mobilephone.R
import com.scb.mvp_mobilephone.model.MobileListResponse
import kotlinx.android.synthetic.main.mobile_list.view.*


class MobileListAdapter(
    private var mobiles: ArrayList<MobileListResponse>,
    private val onMobileItemClicked: OnMobileItemClicked
) :
    RecyclerView.Adapter<MobileViewHolder>() {

    override fun getItemCount(): Int {
        return mobiles.count()
    }

    fun setMobile(mobiles: List<MobileListResponse>) {
        this.mobiles.clear()
        this.mobiles.addAll(mobiles)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MobileViewHolder {
        return MobileViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MobileViewHolder, position: Int) {
        val item = mobiles[position]
        holder.bind(item, onMobileItemClicked)
    }

    interface OnMobileItemClicked {

        fun onMobileClick(mobile: MobileListResponse)

        fun onFavoriteClick(mobile: MobileListResponse)
    }

}

class MobileViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.mobile_list, parent, false)
) {

    private val mobileName = itemView.list_mobile_name
    private val mobileDescription = itemView.list_mobile_description
    private val mobileImage = itemView.list_mobile_image
    private val mobilePrice = itemView.list_mobile_price
    private val mobileRating = itemView.list_mobile_rating
    private val favoriteBtn = itemView.favourite_button
    private val cardItem = itemView.item_card

    private fun mapData(mobile: MobileListResponse): Map<String, Any> {
        return mapOf<String, Any>(
            "id" to mobile.id,
            "name" to mobile.name,
            "brand" to mobile.brand,
            "thumbImageURL" to mobile.thumbImageURL,
            "description" to mobile.description,
            "price" to "Price: $${mobile.price}",
            "rating" to "Rating: ${mobile.rating}"
        )
    }

    fun bind(mobileItem: MobileListResponse, mListener: MobileListAdapter.OnMobileItemClicked) {

        var mapItem = mapData(mobileItem)
        mobileName.text = mapItem["name"].toString()
        mobileDescription.text = mapItem["description"].toString()
        mobilePrice.text = mapItem["price"].toString()
        mobileRating.text = mapItem["rating"].toString()

        Glide.with(mobileImage.context)
            .load(mapItem["thumbImageURL"].toString())
            .apply(RequestOptions.centerCropTransform())
            .into(mobileImage)

        setFavoriteButton(mobileItem.isFav)

        cardItem.setOnClickListener {
            mListener.onMobileClick(mobileItem)
        }

        favoriteBtn.setOnClickListener {
            mListener.onFavoriteClick(mobileItem)
            setFavoriteButton(mobileItem.isFav)
        }
    }

    private fun setFavoriteButton(isFav: Boolean) {
        if (isFav) {
            favoriteBtn.setBackgroundResource(R.drawable.heart_icon_select)
        } else {
            favoriteBtn.setBackgroundResource(R.drawable.heart_icon)
        }
    }
}
