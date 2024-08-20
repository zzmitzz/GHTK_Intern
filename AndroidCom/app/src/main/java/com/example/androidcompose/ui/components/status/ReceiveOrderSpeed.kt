package com.example.androidcompose.ui.components.status

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.androidcompose.R

@Composable
fun ReceiveOrderSpeed() {
    Column {
        Row {
            Image(painter = painterResource(id = R.drawable.rocket_launch_24dp_000000_fill0_wght400_grad0_opsz24), contentDescription = "")
            Text(text = "Tên lửa", color = Color.Black, fontWeight = FontWeight.Bold)
        }
        Text(text = "Tốc độ nhận", color = Color.Black, fontSize = 13.sp )
    }
}