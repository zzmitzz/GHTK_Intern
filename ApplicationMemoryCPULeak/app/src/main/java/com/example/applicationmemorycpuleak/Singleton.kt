package com.example.applicationmemorycpuleak

import android.annotation.SuppressLint
import android.content.Context

object Singleton {
    @SuppressLint("StaticFieldLeak")
    private var context: Context? = null
    fun getContext(context: Context){
        this.context = context
    }
}