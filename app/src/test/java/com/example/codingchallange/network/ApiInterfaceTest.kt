package com.example.codingchallange.network

import com.example.codingchallange.Helper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class ApiInterfaceTest {

    private lateinit var mockserver: MockWebServer
    lateinit var apiInterface: ApiInterface

    @Before
    fun setUp() {
        mockserver = MockWebServer()
        apiInterface = Retrofit.Builder().baseUrl(mockserver.url("/"))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiInterface::class.java)
    }

    @Test
    fun `should return empty acromine list`() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockserver.enqueue(mockResponse)

        val res = apiInterface.getAcromine("nas")
        mockserver.takeRequest()

        Assert.assertEquals(true, res.body()?.isEmpty())
    }

    @Test
    fun `should return acromine list`() = runTest {
        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockserver.enqueue(mockResponse)

        val res = apiInterface.getAcromine("nas")
        mockserver.takeRequest()

        Assert.assertEquals(false, res.body()?.isEmpty())
        Assert.assertEquals(1, res.body()?.size)
    }

    @Test
    fun `should return error`() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something went wrong")
        mockserver.enqueue(mockResponse)

        val res = apiInterface.getAcromine("nas")
        mockserver.takeRequest()

        Assert.assertEquals(false, res.isSuccessful)
        Assert.assertEquals(404, res.code())
    }

    @After
    fun tearDown() {
        mockserver.shutdown()
    }
}