package com.example.musicserviceapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM : ViewModel, T: ViewBinding> : Fragment() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding()
        initObserver()
        initListener()
        return binding.root
    }

    abstract fun initViewModel()

    abstract fun initBinding()

    abstract fun initObserver()

    abstract fun initListener()
}