package com.scb.mvp_mobilephone.view.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scb.mvp_mobilephone.R
import com.scb.mvp_mobilephone.model.MobileListResponse
import com.scb.mvp_mobilephone.model.SortEnum
import com.scb.mvp_mobilephone.model.database.DatabaseHelper
import com.scb.mvp_mobilephone.presenter.*
import com.scb.mvp_mobilephone.view.activity.MobileDetailActivity
import com.scb.mvp_mobilephone.view.adapter.MobileListAdapter
import kotlinx.android.synthetic.main.fragment_mobile_list.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MobileListFragment :
    Fragment(),
    MobileListContract.MobileView,
    MobileListAdapter.OnMobileItemClicked,
    Sortable,
    Updatable {

    override fun showSortData(sortType: SortEnum) {
        presenter.sortData(sortType)
    }

    override fun onFavoriteClick(mobile: MobileListResponse) {
        presenter.onFavoriteClicked(mobile)
    }

    override fun onMobileClick(mobile: MobileListResponse) {
        presenter.onMobileItemClicked(mobile)
    }

    override fun updateData(mobileId: Int) {
        presenter.updateFavoriteButton(mobileId)
    }

    private var mobileListAdapter: MobileListAdapter =
        MobileListAdapter(ArrayList(), this)
    lateinit var presenter: MobileListContract.MobilePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val mobileListView = inflater.inflate(R.layout.fragment_mobile_list, container, false)

        mobileListView.mobileRecyclerView.let {

            // important
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = mobileListAdapter
        }

        presenter = MobilePresenter(this, MobileRepositoryImpl(), DatabaseHelper(requireContext()))
        presenter.getAllMobilesList()

        return mobileListView
    }

    override fun showAllMobilesList(mobiles: List<MobileListResponse>) {
        mobileListAdapter.setMobile(mobiles)
    }

    override fun showDetail(mobile: MobileListResponse) {
        val intent = Intent(context, MobileDetailActivity::class.java)
        intent.putExtra("data", mobile)
        MobileDetailActivity.startActivity(requireContext(), intent)
    }

    override fun onSuccess(resId: Int) {
        Toast.makeText(context, getString(resId), Toast.LENGTH_SHORT).show()
    }

    override fun onFail(resId: Int) {
        Toast.makeText(context, getString(resId), Toast.LENGTH_LONG).show()
    }

}
