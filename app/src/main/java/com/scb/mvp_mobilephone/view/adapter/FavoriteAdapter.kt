package com.scb.mvp_mobilephone.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.scb.mvp_mobilephone.R
import com.scb.mvp_mobilephone.model.MobileListResponse
import kotlinx.android.synthetic.main.favorite_list.view.*

class FavoriteAdapter(
    private var mobiles: MutableList<MobileListResponse>,
    private val onFavoriteItemClicked: OnFavoriteItemClicked
) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun getItemCount(): Int {
        return mobiles.count()
    }

    fun setFavorite(mobiles: List<MobileListResponse>) {
        this.mobiles.clear()
        this.mobiles.addAll(mobiles)
        this.notifyDataSetChanged()
    }

    fun clearFavorite() {
        this.mobiles.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = mobiles[position]
        holder.bind(item, onFavoriteItemClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(parent)
    }

    interface OnFavoriteItemClicked {
        fun onFavoriteClick(mobile: MobileListResponse)
    }

}

class FavoriteViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.favorite_list, parent, false)
) {

    private val favoriteName = itemView.favorite_mobile_name
    private val favoriteImage = itemView.favorite_mobile_image
    private val favoritePrice = itemView.favorite_mobile_price
    private val favoriteRating = itemView.favorite_mobile_rating
    private val cardItem = itemView.favorite_card

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

    fun bind(mobileItem: MobileListResponse, mListener: FavoriteAdapter.OnFavoriteItemClicked) {

        var mapItem = mapData(mobileItem)
        favoriteName.text = mapItem["name"].toString()
        favoritePrice.text = mapItem["price"].toString()
        favoriteRating.text = mapItem["rating"].toString()

        Glide.with(favoriteImage.context)
            .load(mapItem["thumbImageURL"].toString())
            .apply(RequestOptions.centerCropTransform())
            .into(favoriteImage)

        cardItem.setOnClickListener {
            mListener.onFavoriteClick(mobileItem)
        }
    }

}
