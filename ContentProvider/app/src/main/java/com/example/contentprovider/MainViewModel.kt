package com.example.contentprovider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contentprovider.data.ContactRepository
import com.example.contentprovider.data.ContactSchema
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ContactRepository,
) : ViewModel() {
    var contacts: MutableLiveData<List<ContactSchema>?> = MutableLiveData()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun fetchContact() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000L)
            contacts.value = repository.fetchContact()
            _isLoading.value = false
        }
    }
}
