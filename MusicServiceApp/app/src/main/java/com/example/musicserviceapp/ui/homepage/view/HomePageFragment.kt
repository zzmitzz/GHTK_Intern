package com.example.musicserviceapp.ui.homepage.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicserviceapp.MainViewModel
import com.example.musicserviceapp.base.BaseFragment
import com.example.musicserviceapp.databinding.BottomViewMusicBinding
import com.example.musicserviceapp.databinding.FragmentMusicPlayerBinding

class HomePageFragment : BaseFragment<MainViewModel, FragmentMusicPlayerBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun initBinding() {
        binding = FragmentMusicPlayerBinding.inflate(layoutInflater)
    }

    override fun initObserver() {
    }

    override fun initListener() {
    }
}