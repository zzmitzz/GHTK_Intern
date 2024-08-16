package com.example.contentprovider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contentprovider.data.ContactRepository
import com.example.contentprovider.data.model.ContactSchema
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ContactRepository,
) : ViewModel() {
    var contacts: MutableLiveData<List<ContactSchema>?> = MutableLiveData()
    var currentContact: ContactSchema? = null
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun fetchContact() {
        viewModelScope.launch {
            _isLoading.value = true
            contacts.value = repository.fetchContact()
            _isLoading.value = false
        }
    }

    fun setContact(contact: ContactSchema) {
        currentContact = contact
    }

    fun getDetailContact() {
        currentContact?.let {
            viewModelScope.launch {
                _isLoading.value = true
                repository.getDetailContact(it.id)
                _isLoading.value = false
            }
        }
    }
}
