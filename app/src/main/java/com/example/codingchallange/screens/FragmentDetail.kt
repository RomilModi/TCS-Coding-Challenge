package com.example.codingchallange.screens

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.codingchallange.R
import com.example.codingchallange.databinding.FragmentDetailBinding
import com.example.codingchallange.utils.viewBinding

class FragmentDetail : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val args by navArgs<FragmentDetailArgs>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val obj = args.data
        binding.tvData.text = obj.lf
        binding.btnPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}