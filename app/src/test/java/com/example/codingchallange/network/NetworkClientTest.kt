package com.example.codingchallange.network

import com.example.codingchallange.network.NetworkClient
import org.junit.Test
import retrofit2.Retrofit

class NetworkClientTest {

    @Test
    fun testRetrofitInstance() {
        //Get an instance of Retrofit
        val instance: Retrofit = NetworkClient.getInstance()
        //Assert that, Retrofit's base url matches to our BASE_URL
        assert(instance.baseUrl().toUrl().toString() == NetworkClient.BASE_URL)
    }

}