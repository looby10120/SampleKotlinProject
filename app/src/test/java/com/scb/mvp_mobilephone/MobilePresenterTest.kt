package com.scb.mvp_mobilephone

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.*
import com.scb.mvp_mobilephone.model.MobileListResponse
import com.scb.mvp_mobilephone.model.SortEnum
import com.scb.mvp_mobilephone.model.database.DatabaseHelper
import com.scb.mvp_mobilephone.presenter.MobileCallback
import com.scb.mvp_mobilephone.presenter.MobileListContract
import com.scb.mvp_mobilephone.presenter.MobilePresenter
import com.scb.mvp_mobilephone.presenter.MobileRepository
import mockit.Deencapsulation
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class MobilePresenterTest {

    private lateinit var view: MobileListContract.MobileView
    private lateinit var presenter: MobilePresenter
    private lateinit var repository: MobileRepository

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
        repository = mock()
        presenter = MobilePresenter(view, repository, databaseHelper)
        mobile = MobileListResponse(
            "Samsung",
            "Moto E4 Plus.",
            1,
            "Moto E4 Plus",
            179.99,
            4.9,
            "image1",
            false
        )

        mobiles = listOf(
            MobileListResponse("Samsung", "Moto E4 Plus.", 1, "Moto E4 Plus", 179.99, 4.5, "image1", false),
            MobileListResponse("Huawei", "Huawei P30.", 2, "Huawei P30", 279.99, 4.9, "image2", false),
            MobileListResponse("Sony", "Xperia XZ.", 3, "Xperia XZ", 169.00, 4.2, "image3", false)
        )
    }

    @Test
    fun `getAllMobileList Success`() {
        `when`(repository.getList(any())).then {
            (it.getArgument(0) as MobileCallback).callbackSuccess(mobiles)
        }
        presenter.getAllMobilesList()
        verify(view).showAllMobilesList(mobiles)
        verify(view).onSuccess(R.string.success)
    }

    @Test
    fun `getAllMobileList Fail By NullData`() {
        doAnswer {
            val callback: MobileCallback = it.getArgument(0)
            callback.callbackFail(R.string.null_data)
        }.whenever(repository).getList(any())

        presenter.getAllMobilesList()
        verify(view).onFail(R.string.null_data)
    }

    @Test
    fun `getAllMobileList Fail By Connection`() {
        `when`(repository.getList(any())).then {
            (it.getArgument(0) as MobileCallback).callbackFail(R.string.connection_error)
        }
        presenter.getAllMobilesList()
        verify(view).onFail(R.string.connection_error)
    }

    @Test
    fun onMobileItemClicked_shouldShowPhoneDetail() {
        presenter.onMobileItemClicked(mobile)
        verify(view).showDetail(mobile)
    }

    @Test
    fun `onFavoriteClicked Should AddFavorite`() {
        whenever(databaseHelper.getSelectPhone(mobile.name)).thenReturn(false)

        presenter.onFavoriteClicked(mobile)

        verify(databaseHelper).insertData(mobile)
        assertTrue(mobile.isFav)
    }

    @Test
    fun `onFavoriteClicked Should DeleteFavorite`() {
        whenever(databaseHelper.getSelectPhone(mobile.name)).thenReturn(true)

        presenter.onFavoriteClicked(mobile)

        verify(databaseHelper).deleteData(mobile.id)
        assertFalse(mobile.isFav)
    }

    @Test
    fun `sortData with Price Low to High`() {
        var mobileSorted = mobiles.sortedWith(compareBy { it.price })
        Deencapsulation.setField(presenter, "mobiles", mobiles)
        Deencapsulation.setField(presenter, "mobilesSorted", mobileSorted)

        presenter.sortData(SortEnum.PRICE_LOW_TO_HIGH)

        var item = Deencapsulation.getField<List<MobileListResponse>>(presenter, "mobilesSorted")

        verify(view).showAllMobilesList(mobileSorted)
        assertEquals(mobileSorted, item)
    }

    @Test
    fun `sortData with Price High to Low`() {
        var mobileSorted = mobiles.sortedWith(compareBy { it.price }).reversed()
        Deencapsulation.setField(presenter, "mobiles", mobiles)
        Deencapsulation.setField(presenter, "mobilesSorted", mobileSorted)

        presenter.sortData(SortEnum.PRICE_HIGH_TO_LOW)

        var item = Deencapsulation.getField<List<MobileListResponse>>(presenter, "mobilesSorted")

        verify(view).showAllMobilesList(mobileSorted)
        assertEquals(mobileSorted, item)
    }

    @Test
    fun `sortData with Rating 5-1`() {
        var mobileSorted = mobiles.sortedWith(compareBy { it.rating }).reversed()
        Deencapsulation.setField(presenter, "mobiles", mobiles)
        Deencapsulation.setField(presenter, "mobilesSorted", mobileSorted)

        presenter.sortData(SortEnum.RATING_5_1)

        var item = Deencapsulation.getField<List<MobileListResponse>>(presenter, "mobilesSorted")

        verify(view).showAllMobilesList(mobileSorted)
        assertEquals(mobileSorted, item)
    }

}
