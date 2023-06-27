package com.example.codingchallange.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.codingchallange.repository.IFetchAcromineRepository
import com.example.codingchallange.viewmodel.AcromineViewModel

class AcromineViewModelFactory(private val repository: IFetchAcromineRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AcromineViewModel(repository) as T
    }
}