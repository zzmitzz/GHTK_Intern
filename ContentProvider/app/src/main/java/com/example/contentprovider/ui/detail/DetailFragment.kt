package com.example.contentprovider.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.contentprovider.MainViewModel
import com.example.contentprovider.ViewModelFactory
import com.example.contentprovider.base.BaseFragment
import com.example.contentprovider.databinding.DetailFragmentBinding

class DetailFragment : BaseFragment<MainViewModel, DetailFragmentBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = super.onCreateView(inflater, container, savedInstanceState)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelFactory(requireActivity().application),
            )[MainViewModel::class.java]
    }

    override fun initBinding() {
        binding = DetailFragmentBinding.inflate(layoutInflater)
    }

    override fun initObserver() {
    }

    override fun initListener() {
        binding.editIcon.setOnClickListener {
            // TODO
        }
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        Log.d("TAG", "initListener: ${viewModel.currentContact}")
        viewModel.currentContact?.let {
            viewModel.getDetailContact()
        }
    }
}
