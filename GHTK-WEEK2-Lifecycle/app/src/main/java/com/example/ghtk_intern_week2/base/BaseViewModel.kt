package com.example.ghtk_intern_week2.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    fun showLoadingDialog(){
        isLoading.value = true
        Log.d("TAG", "showLoadingDialog: ${isLoading.value}")
    }
    fun hideLoadingDialog(){
        isLoading.value = false
    }
}