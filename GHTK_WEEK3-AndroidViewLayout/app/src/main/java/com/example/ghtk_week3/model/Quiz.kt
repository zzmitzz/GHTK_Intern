package com.example.ghtk_week3.model

data class Quiz(
    val imageSource: Int,
    val stringCorresponding: String,
    var checked: Boolean
)

enum class Answer {
    CORRECT,
    INCORRECT
}