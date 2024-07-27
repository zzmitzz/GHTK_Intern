package com.example.ghtk_intern_week2.model.network.response

import com.google.gson.annotations.SerializedName

data class History(
    @SerializedName("title")
    val title: String,
    @SerializedName("is_up")
    val is_up: Boolean
)
