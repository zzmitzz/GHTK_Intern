package com.example.ghtk_intern_week2.data.repository

import com.example.ghtk_intern_week2.model.network.response.Response

interface UserRepository {
    suspend fun getProfileInformation(): Response
}