package com.example.androidcompose.ui.components.status

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


@Composable
fun Review(like: Int, disLike: Int) {
    Column {
        Row {
            Text(text = "38", color = Color.Black, fontWeight = FontWeight.Bold)
            Icon(imageVector = Icons.Sharp.Face, contentDescription = null)
            Text(text = "10", color = Color.Black)
            Icon(imageVector = Icons.Sharp.Face, contentDescription = null)
        }
        Text(text = "Đánh giá", color = Color.Black)
    }
}