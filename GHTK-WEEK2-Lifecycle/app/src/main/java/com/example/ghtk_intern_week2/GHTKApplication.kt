package com.example.ghtk_intern_week2

import android.app.Application
import com.example.ghtk_intern_week2.data.network.RetrofitBuilder
import com.example.ghtk_intern_week2.utils.Constant
import retrofit2.Retrofit

class GHTKApplication : Application() {
    public lateinit var retrofit: Retrofit
    override fun onCreate() {
        super.onCreate()
        retrofit = RetrofitBuilder.getRetrofit(Constant.BASE_URL)
    }

}