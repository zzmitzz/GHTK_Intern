package com.example.androidcompose.ui.components.status

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Success(a: Int) {
    Column(modifier = Modifier.width(a.dp)) {
        Text(text = "--", color = Color.Black, fontWeight = FontWeight.Bold)
        Text(text = "Thành công", color = Color.Black, fontSize = 13.sp)
    }
}
