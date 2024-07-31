package com.example.coroutines

import androidx.lifecycle.ViewModel
import com.example.coroutines.adapter.ClockAdapter
import com.example.coroutines.adapter.CountTime

class MainViewModel : ViewModel() {
    private var _listClock: MutableList<CountTime> = mutableListOf<CountTime>()
    val listClock
        get() = _listClock


}