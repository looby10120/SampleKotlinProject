package com.scb.mvp_mobilephone.view.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.scb.mvp_mobilephone.model.SortEnum
import com.scb.mvp_mobilephone.presenter.Sortable
import com.scb.mvp_mobilephone.presenter.Updatable
import com.scb.mvp_mobilephone.view.adapter.PageAdapter

class MainActivity : AppCompatActivity(), Updatable {

    private lateinit var sectionsPagerAdapter: PageAdapter
    lateinit var updatable: Updatable
    private var mobileId = 0
    private var fragmentAdapter: PageAdapter? = null
    private var sortable: ArrayList<Sortable> = arrayListOf()
    private var selected = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.scb.mvp_mobilephone.R.layout.activity_main)

        val toolbar: Toolbar = findViewById(com.scb.mvp_mobilephone.R.id.toolbar)
        toolbar.inflateMenu(com.scb.mvp_mobilephone.R.menu.sort_menu)

        toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == com.scb.mvp_mobilephone.R.id.action_sort) {
                sorting()
            } else {
                // do something
            }

            false
        }

        sectionsPagerAdapter = PageAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(com.scb.mvp_mobilephone.R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter.also { fragmentAdapter = it }
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(com.scb.mvp_mobilephone.R.id.fragment_tab)
        tabs.setupWithViewPager(viewPager)

        // custom tabs
        for (i in 0 until tabs.tabCount) {
            val tab: TabLayout.Tab? = tabs.getTabAt(i)
            tab!!.customView = sectionsPagerAdapter.getTabView(i)
        }
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        if (fragment is Sortable) {
            sortable.add(fragment as Sortable)
        }

        if (fragment is Updatable) {
            updatable = fragment
        }
    }

    override fun updateData(mobileId: Int) {
        this.mobileId = mobileId
        updatable.updateData(mobileId)
    }

    private fun sorting() {

        val sortType = arrayOf(
            SortEnum.PRICE_LOW_TO_HIGH,
            SortEnum.PRICE_HIGH_TO_LOW,
            SortEnum.RATING_5_1
        )

        val sortTag = Array(sortType.size) { i -> sortType[i].getString() }

        val builder = AlertDialog.Builder(this)
        lateinit var dialog: AlertDialog

        builder.setSingleChoiceItems(sortTag, selected) { dialogInterface, i ->
            sortable.forEach { it.showSortData(sortType[i]) }
            selected = i
            dialogInterface.dismiss()
        }

        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

}
