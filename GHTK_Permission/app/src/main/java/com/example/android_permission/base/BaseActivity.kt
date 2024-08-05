package com.example.android_permission.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
        initListener()
        observeLiveData()
        requestRuntimePermission()
    }

    abstract fun setView()

    abstract fun initListener()

    abstract fun observeLiveData()

    abstract fun requestRuntimePermission()
}
