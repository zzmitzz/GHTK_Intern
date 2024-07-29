package com.example.android_72_ghtk_rcv.model

data class OrderStatus(
    val id: String,
    val name: String,
    val contact: List<String>,
    val status: Boolean,
    val image: String,
    val detail: String,
    val tag: List<String>
)


