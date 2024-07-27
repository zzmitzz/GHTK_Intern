package com.example.ghtk_intern_week2.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private var retrofit: Retrofit? = null

    fun getRetrofit(api : String): Retrofit {
        return if(retrofit == null){
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(api.trim())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit!!
        } else retrofit!!
    }
}