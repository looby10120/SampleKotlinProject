package com.scb.mvp_mobilephone.view.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scb.mvp_mobilephone.R
import com.scb.mvp_mobilephone.model.MobileListResponse
import com.scb.mvp_mobilephone.model.SortEnum
import com.scb.mvp_mobilephone.model.database.DatabaseHelper
import com.scb.mvp_mobilephone.presenter.FavoriteContract
import com.scb.mvp_mobilephone.presenter.FavoritePresenter
import com.scb.mvp_mobilephone.presenter.Sortable
import com.scb.mvp_mobilephone.presenter.Updatable
import com.scb.mvp_mobilephone.view.activity.MobileDetailActivity
import com.scb.mvp_mobilephone.view.adapter.FavoriteAdapter
import kotlinx.android.synthetic.main.fragment_favorite_list.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteListFragment :
    Fragment(),
    FavoriteContract.FavoriteView,
    FavoriteAdapter.OnFavoriteItemClicked,
    Sortable {

    override fun onFavoriteClick(mobile: MobileListResponse) {
        presenter.onFavoriteItemClicked(mobile)
    }

    private var favoriteAdapter: FavoriteAdapter =
        FavoriteAdapter(ArrayList(), this)
    lateinit var presenter: FavoriteContract.FavoritePresenter
    lateinit var updatable: Updatable
    private var isShow = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val favoriteListView = inflater.inflate(R.layout.fragment_favorite_list, container, false)

        favoriteListView.favoriteRecyclerView.let {

            // important
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = favoriteAdapter
        }

        presenter = FavoritePresenter(this, DatabaseHelper(requireContext()))

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                presenter.deleteFavorite(viewHolder.adapterPosition)
                presenter.sendDataToActivity(updatable)
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(favoriteListView.favoriteRecyclerView)

        return favoriteListView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is Updatable) {
            updatable = activity as Updatable
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isShow) {
            presenter.getAllFavoriteList()
            isShow = false
        } else if (!isVisibleToUser && !isShow) {
            isShow = true
            presenter.clearData()
        }
    }

    override fun showAllFavoriteList(mobiles: List<MobileListResponse>) {
        favoriteAdapter.setFavorite(mobiles)
    }

    override fun showDetail(mobile: MobileListResponse) {
        val intent = Intent(context, MobileDetailActivity::class.java)
        intent.putExtra("data", mobile)
        MobileDetailActivity.startActivity(requireContext(), intent)
    }

    override fun clearFavoriteList() {
        favoriteAdapter.clearFavorite()
    }

    override fun showSortData(sortType: SortEnum) {
        presenter.sortData(sortType)
    }

    override fun swipeToDelete(mobiles: List<MobileListResponse>) {
        favoriteAdapter.setFavorite(mobiles)
    }

}
