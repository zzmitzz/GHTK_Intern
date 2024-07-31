package com.example.coroutines.adapter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountTime() {
    private var currentTime = 0L
    var livedataTime = MutableLiveData<Long>(currentTime)
    var isRunning = false
    private var dispatcher = Dispatchers.Default
    private var job: Job = Job()
    private var coroutineScope = CoroutineScope(dispatcher + job)

    fun getState(): Boolean{
        return isRunning
    }
    fun start(){
        if(!getState()){
            job = coroutineScope.launch {
                while(currentTime < 3600000L){
                    delay(10L)
                    currentTime += 10
                    livedataTime.postValue(currentTime)
                }
            }
        }
        isRunning = true
    }
    fun reset(){
        stop()
        currentTime = 0
        isRunning = false
        livedataTime.postValue(currentTime)
    }
    fun stop(){
        if(getState()){
            job.cancel()
        }
        isRunning = false
    }

}