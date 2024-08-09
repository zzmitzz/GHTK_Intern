package com.example.bluetoothmessage.ui.chat.model

data class Message(
    val text: String,
    val date: Long,
    val isMine: Boolean,
)
