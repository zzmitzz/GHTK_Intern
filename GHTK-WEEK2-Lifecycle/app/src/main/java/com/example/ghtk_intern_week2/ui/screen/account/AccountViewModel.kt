package com.example.ghtk_intern_week2.ui.screen.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ghtk_intern_week2.base.BaseViewModel
import com.example.ghtk_intern_week2.data.repository.UserRepositoryImpl
import com.example.ghtk_intern_week2.model.network.response.History
import com.example.ghtk_intern_week2.model.network.response.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel(val userRepositoryImpl: UserRepositoryImpl) : BaseViewModel() {
    private var result : Response? = null
    private var _historyList : MutableLiveData<List<History>> = MutableLiveData()
    var userName: MutableLiveData<String> = MutableLiveData()

    public val historyList : LiveData<List<History>>
        get() = _historyList
    fun fetchingData(){
        viewModelScope.launch {
            showLoadingDialog()
            withContext(Dispatchers.IO){
                result  = userRepositoryImpl.getProfileInformation()
                _historyList.postValue(result?.history)
                userName.postValue(result?.fullName)
                delay(1500)
            }
            hideLoadingDialog()
        }
    }

}