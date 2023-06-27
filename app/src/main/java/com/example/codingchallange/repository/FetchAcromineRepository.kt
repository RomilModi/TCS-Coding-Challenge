package com.example.codingchallange.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.codingchallange.models.AcromineData
import com.example.codingchallange.models.NetworkResult
import com.example.codingchallange.network.ApiInterface

interface IFetchAcromineRepository {
    val listOfAcromine: LiveData<NetworkResult<AcromineData>>
    suspend fun fetchAcromineData(data: String)
    fun clearData()
}

class FetchAcromineRepository(private val apiInterface: ApiInterface) : IFetchAcromineRepository {

    private var _listOfAcromine: MutableLiveData<NetworkResult<AcromineData>> = MutableLiveData()
    override val listOfAcromine: LiveData<NetworkResult<AcromineData>>
        get() = _listOfAcromine

    /**
     * To fetch Acromine data from server
     * @param data - User Entered data
     */
    override suspend fun fetchAcromineData(data: String) {
        _listOfAcromine.postValue(NetworkResult.Loading())
        try {
            val response = apiInterface.getAcromine(data)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.let {
                    _listOfAcromine.postValue(NetworkResult.Success(it))
                }
            } else {
                _listOfAcromine.postValue(NetworkResult.Error(response.message()))
            }
        } catch (e: Exception) {
            _listOfAcromine.postValue(NetworkResult.Error("Something went wrong. please try again later."))
        }
    }

    /**
     * clear model
     */
    override fun clearData() {
        _listOfAcromine.postValue(NetworkResult.Success(null))
    }
}