package com.example.codingchallange.network

import com.example.codingchallange.models.AcromineData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("acromine/dictionary.py")
    suspend fun getAcromine(@Query("sf") text: String): Response<AcromineData>
}