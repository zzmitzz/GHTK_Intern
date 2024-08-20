package com.example.applicationmemorycpuleak

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun makeMemoryLeak(){
        // Prefer using viewModelScope, but now don't
        CoroutineScope(Dispatchers.IO).launch {
            delay(1550000)
        }
    }
}