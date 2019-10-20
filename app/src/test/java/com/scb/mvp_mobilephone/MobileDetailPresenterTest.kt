package com.scb.mvp_mobilephone

import com.nhaarman.mockitokotlin2.*
import com.scb.mvp_mobilephone.model.MobileImageResponse
import com.scb.mvp_mobilephone.model.MobileListResponse
import com.scb.mvp_mobilephone.presenter.DetailCallback
import com.scb.mvp_mobilephone.presenter.DetailRepository
import com.scb.mvp_mobilephone.presenter.MobileDetailContract
import com.scb.mvp_mobilephone.presenter.MobileDetailPresenter
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
class MobileDetailPresenterTest {

    private lateinit var view: MobileDetailContract.DetailView
    private lateinit var presenter: MobileDetailPresenter
    private lateinit var repository: DetailRepository

    @Mock
    lateinit var mobile: MobileListResponse

    @Mock
    lateinit var mobiles: List<MobileListResponse>

    @Mock
    lateinit var images: List<MobileImageResponse>

    @Before
    fun setup() {

        view = mock()
        repository = mock()
        presenter = MobileDetailPresenter(view, repository)
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

        images = listOf(
            MobileImageResponse(1, 1, "image1"),
            MobileImageResponse(2, 1, "image2"),
            MobileImageResponse(3, 1, "image3")
        )
    }

    @Test
    fun `getMobileImage Success`() {
        `when`(repository.getPicture(any(), any())).then {
            (it.getArgument(1) as DetailCallback).callbackSuccess(images)
        }
        presenter.getMobilePicture(mobile.id)
        verify(view).showMobileImage(images)
        verify(view).onSuccess(R.string.image_success)
    }

    @Test
    fun `getMobileImage Fail By NullData`() {
        doAnswer {
            val callback: DetailCallback = it.getArgument(1)
            callback.callbackFail(R.string.null_data)
        }.whenever(repository).getPicture(any(), any())

        presenter.getMobilePicture(mobile.id)
        verify(view).onFail(R.string.null_data)
    }

    @Test
    fun `getMobileImage Fail By Connection`() {
        `when`(repository.getPicture(any(), any())).then {
            (it.getArgument(1) as DetailCallback).callbackFail(R.string.connection_error)
        }
        presenter.getMobilePicture(mobile.id)
        verify(view).onFail(R.string.connection_error)
    }

    @Test
    fun `getMobileDetail should ShowMobileDetail`() {
        presenter.getMobileDetail(mobile)
        verify(view).showMobileDetail(mobile)
    }


}
