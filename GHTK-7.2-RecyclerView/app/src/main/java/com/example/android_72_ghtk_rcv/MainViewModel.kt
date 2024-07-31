package com.example.android_72_ghtk_rcv

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_72_ghtk_rcv.adapter.ItemAdapter
import com.example.android_72_ghtk_rcv.model.OrderStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val _adapter by lazy {
        ItemAdapter()
    }
    val adapter get() = _adapter
    private val list: MutableList<OrderStatus> = mutableListOf()

    fun initAdapter(callback : () -> Unit){
        viewModelScope.launch{
            stimulateFetchDataFromNetwork()
            _adapter.setData(list)
            withContext(Dispatchers.Main){
                callback()
            }
        }
    }
    suspend private fun stimulateFetchDataFromNetwork(){
        delay(2000)
        val data = listOf(
            OrderStatus(
                id = "1",
                name = "Nguyen Van A",
                contact = listOf("Home", "Phone"),
                status = false,
                image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-Hyds8h5bI6h9Xa4czHnLTa94FTwXP9aHLw&s",
                detail = "",
                tag = listOf("funny", "short", "words")
            ),
            OrderStatus(
                id = "2",
                name = "Tran Thi B",
                contact = listOf("Phone", "Flag"),
                status = false,
                image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-Hyds8h5bI6h9Xa4czHnLTa94FTwXP9aHLw&s",
                detail = "more detail information",
                tag = listOf("random", "funny", "words")
            ),
            OrderStatus(
                id = "3",
                name = "Le Van C",
                contact = listOf("Home", "Flag"),
                status = false,
                image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-Hyds8h5bI6h9Xa4czHnLTa94FTwXP9aHLw&s",
                detail = "",
                tag = listOf("cool", "funny", "words")
            ),
            OrderStatus(
                id = "4",
                name = "Pham Thi D",
                contact = listOf("Phone", "Home"),
                status = true,
                image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-Hyds8h5bI",
                detail = "",
                tag = listOf("funny", "short", "words")
            )
        )
        repeat(6){
            list.addAll(data)
        }
    }

    fun loadMoreItems() {
        CoroutineScope(Dispatchers.IO).launch {
            if(_adapter.itemCount < 100){
                stimulateFetchDataFromNetwork()
                withContext(Dispatchers.Main) {
                    _adapter.notifyItemRangeInserted(list.size, 6)
                }
            }
        }
    }

    fun reloadData() {
        CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {
                list.clear()
                _adapter.notifyDataSetChanged()
                stimulateFetchDataFromNetwork()
                list.addAll(list)
                _adapter.notifyDataSetChanged()
            }
        }
    }
}