package com.scb.mvp_mobilephone

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.scb.mvp_mobilephone.model.ApiManager
import com.scb.mvp_mobilephone.model.MobileApi
import com.scb.mvp_mobilephone.model.MobileListResponse
import com.scb.mvp_mobilephone.model.database.DatabaseHelper
import com.scb.mvp_mobilephone.presenter.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class MobileRepositoryTest {

    private lateinit var view: MobileListContract.MobileView
    private lateinit var presenter: MobilePresenter
    private lateinit var repository: MobileRepository
    private lateinit var mobileCallback: MobileCallback
    private lateinit var callback: Callback<List<MobileListResponse>>
    private lateinit var call: Call<List<MobileListResponse>>
    private lateinit var response: Response<List<MobileListResponse>>
    private lateinit var mobileApi: MobileApi

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
        repository = MobileRepositoryImpl()
        mobileCallback = mock()
        callback = mock()
        call = mock()
        response = mock()
        presenter = MobilePresenter(view, repository, databaseHelper)
        mobileApi = spy(ApiManager.mobileApi)
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

    @Ignore("Will fix it later")
    @Test
    fun `getList Success`(){

    }


}
