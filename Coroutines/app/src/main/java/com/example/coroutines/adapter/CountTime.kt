package com.example.coroutines.adapter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountTime() {
    private var currentTime = 0L
    val getCurrentTime: Long
        get() = currentTime

    lateinit var job: Job
    fun start(clockPosition: Int){
        job = CoroutineScope(Dispatchers.Default).launch {
            while(currentTime < 3600000L){
                delay(100L)
                currentTime += 100
            }
        }
        job.start()
    }
    fun reset(){
        job.cancel()
        currentTime = 0
    }
    fun stop(){
        job.cancel()
    }
}