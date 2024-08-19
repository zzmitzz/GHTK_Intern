package com.example.androidcompose.ui.components.status

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


@Composable
fun Orders(a: Int) {
    Column {
        Text(text = "80", color = Color.Black, fontWeight = FontWeight.Bold)
        Text(text = "ĐH đã đặt", color = Color.Black )
    }
}