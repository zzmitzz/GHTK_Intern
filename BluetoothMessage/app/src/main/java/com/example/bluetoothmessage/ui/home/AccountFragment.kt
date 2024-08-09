package com.example.bluetoothmessage.ui.home

import androidx.lifecycle.ViewModelProvider
import com.example.bluetoothmessage.MainViewModel
import com.example.bluetoothmessage.base.BaseFragment
import com.example.bluetoothmessage.databinding.AccountFragmentBinding
import com.example.bluetoothmessage.databinding.ChatchitFragmentBinding
import com.example.bluetoothmessage.view_model.ViewModelFactory

class AccountFragment : BaseFragment<AccountFragmentBinding, MainViewModel>(){
    override fun initBinding() {
        binding = AccountFragmentBinding.inflate(layoutInflater)
    }
    override fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory())[MainViewModel::class.java]
    }

    override fun initListener() {
    }

    override fun initObserver() {
    }
}