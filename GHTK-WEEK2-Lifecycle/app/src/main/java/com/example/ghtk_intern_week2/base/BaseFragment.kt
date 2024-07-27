package com.example.ghtk_intern_week2.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM: BaseViewModel>: Fragment() {
    protected lateinit var viewModel : VM
    protected var loadingDialog: LoadingDialog? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        initListener()
        initLoadingDialog()
        observeLiveData()
    }
    private fun initLoadingDialog(){
        loadingDialog = context?.let {
            LoadingDialog(it)
        }
        activity?.let {
            viewModel.isLoading.observe(it){
                if(it){
                    loadingDialog?.showDialog()
                }else{
                    loadingDialog?.dismissDialog()
                }
            }
        }
    }
    abstract fun initListener() : Unit
    abstract fun observeLiveData()

    abstract fun initViewModel()

    open fun initView() {
    }
}