package com.example.bluetoothmessage.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T: ViewBinding, VM : ViewModel> : Fragment() {
    lateinit var binding: T
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = binding.root

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserver()
        observeLoadingLivedata()
    }

    /** Initialize binding to inflate view, ensure the binding is initialized before the view is inflated */
    abstract fun initBinding()

    /**
     * Initialize view model
     */
    abstract fun initViewModel()

    /**
     * Initialize listener
     */
    abstract fun initListener()

    /**
     * Initialize observer
     */
    abstract fun initObserver()

    open fun observeLoadingLivedata() {
    }
}
