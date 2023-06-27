package com.example.codingchallange.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.codingchallange.R
import com.example.codingchallange.adapters.AcromineViewAdapter
import com.example.codingchallange.databinding.FragmentSearchBinding
import com.example.codingchallange.models.AcromineData
import com.example.codingchallange.models.Lf
import com.example.codingchallange.models.NetworkResult
import com.example.codingchallange.network.ApiInterface
import com.example.codingchallange.network.NetworkClient
import com.example.codingchallange.repository.FetchAcromineRepository
import com.example.codingchallange.utils.viewBinding
import com.example.codingchallange.viewmodel.AcromineViewModel
import com.example.codingchallange.viewmodelfactory.AcromineViewModelFactory


class FragmentSearch : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private lateinit var viewModel: AcromineViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         *  In Actual Project, below objects should be created using DI framework.
         */
        val apiInterface = NetworkClient.getInstance().create(ApiInterface::class.java)
        val repository = FetchAcromineRepository(apiInterface)
        viewModel = ViewModelProvider(
            this, AcromineViewModelFactory(repository)
        )[AcromineViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.listOfAcromineData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    handleSuccess(response.data)
                }

                is NetworkResult.Error -> {
                    Log.i(TAG, "Error received : ${response.message}")
                }

                is NetworkResult.Loading -> {
                    Log.i(TAG, "Loading state received")
                }
            }
        }
    }

    private fun handleSuccess(data: AcromineData?) {
        Log.i(TAG, "Success received")
        val myRecyclerViewAdapter: AcromineViewAdapter
        if (!data.isNullOrEmpty()) {
            data[0].let { acromineDataItem ->
                myRecyclerViewAdapter = AcromineViewAdapter(acromineDataItem.lfs) { itemData ->
                    navigateToNextScreen(itemData)
                }
            }
        } else {
            myRecyclerViewAdapter = AcromineViewAdapter(emptyList())
        }
        binding.myAdapter = myRecyclerViewAdapter
    }

    private fun navigateToNextScreen(lf: Lf) {
        val directions = FragmentSearchDirections.searchFragmentToDetailFragment(lf)
        findNavController().navigate(directions)
    }

    companion object {
        private const val TAG = "CodingChallenge"
    }
}