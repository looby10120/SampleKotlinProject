package com.scb.mvp_mobilephone

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.*
import com.scb.mvp_mobilephone.model.MobileListResponse
import com.scb.mvp_mobilephone.model.SortEnum
import com.scb.mvp_mobilephone.model.database.DatabaseHelper
import com.scb.mvp_mobilephone.presenter.FavoriteContract
import com.scb.mvp_mobilephone.presenter.FavoritePresenter
import mockit.Deencapsulation
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class FavoritePresenterTest {

    private lateinit var view: FavoriteContract.FavoriteView
    private lateinit var presenter: FavoritePresenter

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Mock
    lateinit var mobile: MobileListResponse

    @Mock
    lateinit var mobiles: List<MobileListResponse>

    @Mock
    private var databaseHelper = spy(DatabaseHelper(context))

    @Before
    fun setup() {

        view = mock()
        presenter = FavoritePresenter(view, databaseHelper)
        mobile = MobileListResponse(
            "Samsung",
            "Moto E4 Plus.",
            1,
            "Moto E4 Plus",
            179.99,
            4.9,
            "image1",
            true
        )

        mobiles = listOf(
            MobileListResponse("Samsung", "Moto E4 Plus.", 1, "Moto E4 Plus", 179.99, 4.5, "image1", true),
            MobileListResponse("Huawei", "Huawei P30.", 2, "Huawei P30", 279.99, 4.9, "image2", true),
            MobileListResponse("Sony", "Xperia XZ.", 3, "Xperia XZ", 169.00, 4.2, "image3", true)
        )
    }

    @Test
    fun `getAllFavoriteList Success`() {
        whenever(databaseHelper.getAllFavorite()).thenReturn(mobiles)
        presenter.getAllFavoriteList()
        verify(view).showAllFavoriteList(mobiles)
    }

    @Test
    fun `clearData should be call`() {
        presenter.clearData()
        verify(view).clearFavoriteList()
    }

    @Test
    fun `onFavoriteItemClicked should showDetail of mobile`() {
        presenter.onFavoriteItemClicked(mobile)
        verify(view).showDetail(mobile)
    }

    @Test
    fun `sortData with Price Low to High`() {
        var favoriteSorted = mobiles.sortedWith(compareBy { it.price })

        whenever(databaseHelper.getAllFavorite()).thenReturn(mobiles)
        Deencapsulation.setField(presenter, "favoriteList", mobiles)
        Deencapsulation.setField(presenter, "favoritesSorted", favoriteSorted)

        presenter.sortData(SortEnum.PRICE_LOW_TO_HIGH)

        var item = Deencapsulation.getField<List<MobileListResponse>>(presenter, "favoritesSorted")

        verify(view).showAllFavoriteList(favoriteSorted)
        verify(databaseHelper, atLeastOnce()).insertData(item!![0])
        assertEquals(favoriteSorted, item)
    }

    @Test
    fun `sortData with Price High to Low`() {
        var favoriteSorted = mobiles.sortedWith(compareBy { it.price }).reversed()

        whenever(databaseHelper.getAllFavorite()).thenReturn(mobiles)
        Deencapsulation.setField(presenter, "favoriteList", mobiles)
        Deencapsulation.setField(presenter, "favoritesSorted", favoriteSorted)

        presenter.sortData(SortEnum.PRICE_HIGH_TO_LOW)

        var item = Deencapsulation.getField<List<MobileListResponse>>(presenter, "favoritesSorted")

        verify(view).showAllFavoriteList(favoriteSorted)
        verify(databaseHelper, atLeastOnce()).insertData(item!![0])
        assertEquals(favoriteSorted, item)
    }

    @Test
    fun `sortData with Rating 5-1`() {
        var favoriteSorted = mobiles.sortedWith(compareBy { it.rating }).reversed()

        whenever(databaseHelper.getAllFavorite()).thenReturn(mobiles)
        Deencapsulation.setField(presenter, "favoriteList", mobiles)
        Deencapsulation.setField(presenter, "favoritesSorted", favoriteSorted)

        presenter.sortData(SortEnum.RATING_5_1)

        var item = Deencapsulation.getField<List<MobileListResponse>>(presenter, "favoritesSorted")

        verify(view).showAllFavoriteList(favoriteSorted)
        verify(databaseHelper, atLeastOnce()).insertData(item!![0])
        assertEquals(favoriteSorted, item)
    }

}
