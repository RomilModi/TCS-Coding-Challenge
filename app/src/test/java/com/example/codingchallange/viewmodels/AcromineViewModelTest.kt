package com.example.codingchallange.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.codingchallange.MainCoroutineRule
import com.example.codingchallange.models.AcromineData
import com.example.codingchallange.models.NetworkResult
import com.example.codingchallange.repository.FetchAcromineRepository
import com.example.codingchallange.viewmodel.AcromineViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AcromineViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @MockK
    lateinit var repository: FetchAcromineRepository

    private lateinit var viewModel: AcromineViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = AcromineViewModel(repository)
    }

    @Test
    fun `should test fetch acromine`() = runTest {
        val slot = slot<String>()
        coEvery { repository.fetchAcromineData(capture(slot)) } answers {
            NetworkResult.Success(
                AcromineData()
            )
        }
        coEvery { repository.clearData() } returns Unit
        viewModel.onTextChanged("nas")
        coVerify(exactly = 1) { viewModel.onTextChanged("nas") }
    }

    @Test
    fun `should verify live data`() = runTest {
        val dataResponse = MutableLiveData<NetworkResult<AcromineData>>()
        coEvery { viewModel.listOfAcromineData } returns dataResponse
        assertEquals(null, viewModel.listOfAcromineData.value)
    }
}