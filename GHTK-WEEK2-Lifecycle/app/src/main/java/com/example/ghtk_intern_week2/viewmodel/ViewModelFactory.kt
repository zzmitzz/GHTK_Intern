package com.example.ghtk_intern_week2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ghtk_intern_week2.MainViewModel
import com.example.ghtk_intern_week2.data.network.ApiService
import com.example.ghtk_intern_week2.data.network.RetrofitBuilder
import com.example.ghtk_intern_week2.data.repository.UserRepositoryImpl
import com.example.ghtk_intern_week2.ui.screen.account.AccountViewModel
import com.example.ghtk_intern_week2.utils.Constant

class ViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel() as T
        }
        else if(modelClass.isAssignableFrom(AccountViewModel::class.java)){
            return AccountViewModel(UserRepositoryImpl(RetrofitBuilder.getRetrofit(Constant.BASE_URL).create(ApiService::class.java))) as T
        }
        throw IllegalArgumentException("View Model not found")
    }
}