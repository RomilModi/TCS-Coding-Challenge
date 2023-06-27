package com.example.codingchallange.repository

import com.example.codingchallange.MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.codingchallange.models.AcromineData
import com.example.codingchallange.network.ApiInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class FetchAcromineRepositoryTest {

    @MockK
    lateinit var apiInterface: ApiInterface

    private lateinit var repository: FetchAcromineRepository

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = FetchAcromineRepository(apiInterface)
    }

    @Test
    fun `should verify success response`() = runTest {
        val slot = slot<String>()
        val observer = mockk<Observer<Any>>()

        val slotState = slot<Any>()

        val list = arrayListOf<Any>()

        repository.listOfAcromine.observeForever(observer)
        coEvery { observer.onChanged(capture(slotState)) } answers {
            list.add(slotState.captured)
        }
        coEvery { apiInterface.getAcromine(capture(slot)) } returns Response.success(AcromineData())
        repository.fetchAcromineData("nas")
        assert(list.isNotEmpty())
        assert(2 == list.size)
    }

    @Test
    fun `should verify error response`() = runTest {
        val slot = slot<String>()
        val observer = mockk<Observer<Any>>()

        val slotState = slot<Any>()

        val list = arrayListOf<Any>()

        repository.listOfAcromine.observeForever(observer)
        coEvery { observer.onChanged(capture(slotState)) } answers {
            list.add(slotState.captured)
        }
        coEvery { apiInterface.getAcromine(capture(slot)) } returns Response.error(
            500, ByteArray(0).toResponseBody(null)
        )
        repository.fetchAcromineData("nas")
        assert(list.isNotEmpty())
        assert(2 == list.size)
    }

    @Test
    fun `should verify error`() = runTest {
        val slot = slot<String>()
        val observer = mockk<Observer<Any>>()

        val slotState = slot<Any>()

        val list = arrayListOf<Any>()

        repository.listOfAcromine.observeForever(observer)
        coEvery { observer.onChanged(capture(slotState)) } answers {
            list.add(slotState.captured)
        }
        coEvery { apiInterface.getAcromine(capture(slot)) } throws Exception()
        repository.fetchAcromineData("nas")
        assert(list.isNotEmpty())
        assert(2 == list.size)
    }

    @Test
    fun `should clear data`() = runTest {
        val observer = mockk<Observer<Any>>()

        val slotState = slot<Any>()

        val list = arrayListOf<Any>()

        repository.listOfAcromine.observeForever(observer)
        coEvery { observer.onChanged(capture(slotState)) } answers {
            list.add(slotState.captured)
        }
        repository.clearData()
        assert(list.isNotEmpty())
        assert(list.size == 1)
    }
}