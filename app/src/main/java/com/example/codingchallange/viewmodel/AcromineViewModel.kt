package com.example.codingchallange.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallange.models.AcromineData
import com.example.codingchallange.models.NetworkResult
import com.example.codingchallange.repository.IFetchAcromineRepository
import kotlinx.coroutines.launch

class AcromineViewModel(private val repository: IFetchAcromineRepository) : ViewModel() {

    val listOfAcromineData: LiveData<NetworkResult<AcromineData>>
        get() = repository.listOfAcromine

    private fun fetchAchromineData(data: String) {
        viewModelScope.launch {
            repository.fetchAcromineData(data)
        }
    }

    /**
     * Get acromine data from server.
     * @param s - User entered data
     * Request will be sent only after entering 2 chars
     */
    fun onTextChanged(s: CharSequence) {
        repository.clearData()
        if (s.length > 2) {
            fetchAchromineData(s.toString())
        }
    }
}