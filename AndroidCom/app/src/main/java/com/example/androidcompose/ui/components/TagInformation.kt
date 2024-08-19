package com.example.androidcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcompose.ui.theme.AndroidComposeTheme


@Composable
fun TagItem(modifier: Modifier, name: String, backgroundColor: Color) {
    Card(
        shape = RoundedCornerShape(3.dp),
        modifier = modifier
    ){
        Box(modifier = Modifier.background(color = backgroundColor)){
            Text(text = name, modifier = Modifier.padding(2.dp), color = Color.White)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagInformation(){
    val listItem: List<String> = listOf("Thiết bị điện từ", "Voucher", "Thiết bị điện gia dụng", "Mẹ và bé", "Nhà cửa")
    val backgroundColor: List<String> = listOf("#4a9cb9", "#df7356", "#2e667f", "#fe7b93", "#c8a225")
    var i = 0
    FlowRow(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Đã mua", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(4.dp))
        listItem.forEach {
            TagItem(name = it, backgroundColor = Color(android.graphics.Color.parseColor(backgroundColor[i++])), modifier = Modifier.padding(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidComposeTheme {
        TagInformation()
    }
}
