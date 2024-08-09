package com.example.bluetoothmessage.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bluetoothmessage.MainViewModel
import com.example.bluetoothmessage.ui.chat.viewmodel.ChatViewModel

class ViewModelFactory() : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel() as T
        }
        else if(modelClass.isAssignableFrom(ChatViewModel::class.java)){
            return ChatViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}