package com.example.ghtk_intern_week2.data.network

import com.example.ghtk_intern_week2.model.network.response.Response
import retrofit2.http.GET

interface ApiService {
    @GET("d/af3cc8b64fa487da8be34c1cc1c5d2d5.json")
    suspend fun getUsers(): Response
}