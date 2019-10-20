package com.scb.mvp_mobilephone.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.scb.mvp_mobilephone.R
import com.scb.mvp_mobilephone.view.fragment.FavoriteListFragment
import com.scb.mvp_mobilephone.view.fragment.MobileListFragment
import kotlinx.android.synthetic.main.custom_tab_layout.view.*

class PageAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val PAGES: Int = 2

    private val TAB_TITLES = arrayOf("MOBILE LIST", "FAVORITE LIST")
    private var mobileListFragment: MobileListFragment? = null
    private var favoriteListFragment: FavoriteListFragment? = null

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                if (mobileListFragment == null) {
                    this.mobileListFragment = MobileListFragment()
                }
                mobileListFragment as MobileListFragment
            }
            else -> {
                if (favoriteListFragment == null) {
                    this.favoriteListFragment = FavoriteListFragment()
                }
                favoriteListFragment as FavoriteListFragment
            }
        }
    }

    override fun getCount(): Int {
        return PAGES
    }

    fun getTabView(position: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.custom_tab_layout, null).apply {
            title.text = TAB_TITLES[position]
        }
    }
}
