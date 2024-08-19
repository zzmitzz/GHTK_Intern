package com.example.androidcompose.ui.components.status

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


@Composable
fun FireWallet(a : Int){
    Column {
        Text(text = "--", color = Color.Black, fontWeight = FontWeight.Bold)
        Text(text = "Độ cháy túi", color = Color.Black )
    }
}

@Composable
fun VisitedShop(a : Int){
    Column {
        Text(text = "10 shop", color = Color.Black, fontWeight = FontWeight.Bold)
        Text(text = "Đã ghé thăm", color = Color.Black )
    }
}

@Composable
fun BoughtShop(a : Int){
    Column {
        Text(text = "11 shop", color = Color.Black, fontWeight = FontWeight.Bold)
        Text(text = "Độ mua", color = Color.Black )
    }
}

@Composable
fun PeriodBuying(a : Int){
    Column {
        Text(text = "--", color = Color.Black, fontWeight = FontWeight.Bold)
        Text(text = "Chu kì mua", color = Color.Black )
    }
}