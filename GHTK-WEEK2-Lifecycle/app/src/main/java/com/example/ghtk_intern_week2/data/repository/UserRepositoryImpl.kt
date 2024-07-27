package com.example.ghtk_intern_week2.data.repository

import com.example.ghtk_intern_week2.data.network.ApiService
import com.example.ghtk_intern_week2.model.network.response.Response
import retrofit2.Retrofit

class UserRepositoryImpl(val network: ApiService) : UserRepository {
    override suspend fun getProfileInformation(): Response {
        return network.getUsers()
    }
}