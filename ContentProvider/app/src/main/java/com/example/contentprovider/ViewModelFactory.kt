package com.example.contentprovider

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.contentprovider.data.ContactRepository
import com.example.contentprovider.data.MyContactDataSource

class ViewModelFactory(
    private val contextApplication: Application,
) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val source =
                MyContactDataSource(contextApplication.contentResolver)
            return MainViewModel(ContactRepository(source)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
