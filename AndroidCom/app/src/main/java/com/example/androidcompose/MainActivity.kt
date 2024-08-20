package com.example.androidcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Phone
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcompose.ui.components.Pager
import com.example.androidcompose.ui.components.StatusBar
import com.example.androidcompose.ui.components.TagInformation
import com.example.androidcompose.ui.components.UserInformation
import com.example.androidcompose.ui.theme.AndroidComposeTheme
import com.example.androidcompose.ui.theme.GHTKColor1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidComposeTheme {
//                Scaffold(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

data class Order(
    val description: String,
    val date: String,
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val orderList =
        listOf(
            Order("YEAHHH! hai đã đặt hàng sản phẩm Giá trị cao", "10/10/2023"),
            Order("YEAHHH! hai đã đặt hàng sản phẩm Thực phẩm khô", "10/10/2023"),
            Order("YEAHHH! hai đã đặt hàng sản phẩm Thực phẩm tươi", "29/09/2023"),
            Order("YEAHHH! phus đã đặt hàng sản phẩm 2023-09-20", "29/09/2023"),
            Order("YEAHHH! phus đã đặt hàng sản phẩm heli, 1", "12/09/2023"),
            Order("YEAHHH! phus đã đặt hàng sản phẩm 090", "12/09/2023"),
        )
    AndroidComposeTheme {
        Column {
            UserInformation()
            Spacer(modifier = Modifier.padding(3.dp))
            TagInformation()
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            StatusBar()
            Divider(modifier = Modifier.height(8.dp))
            Pager()
            Divider()
            Row(modifier = Modifier.fillMaxWidth().padding(end = 5.dp)) {
                LazyColumn(
                    modifier =
                    Modifier
                        .padding(16.dp)
                        .weight(4.4f)
                ) {
                    items(orderList) { order ->
                        Row(
                            modifier =
                            Modifier
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier =
                                Modifier
                                    .weight(2f),
                            ) {
                                Text(
                                    text = order.description,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black,
                                )
                            }
                            Text(
                                text = order.date,
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier =
                                Modifier
                                    .padding(start = 8.dp)
                                    .align(Alignment.CenterVertically),
                            )
                        }
                        Divider(color = Color.Gray, thickness = 0.5.dp)
                    }
                }
                Column(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()) {
                    Box(
                        modifier =
                        Modifier
                            .background(GHTKColor1)
                            .size(70.dp),
                    ) {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Icon(
                                imageVector = Icons.Sharp.Phone,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                            Text(text = "Gọi điện", color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Box(
                        modifier =
                        Modifier
                            .background(GHTKColor1)
                            .size(70.dp)
                            .padding(bottom = 8.dp),
                    ) {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Icon(
                                imageVector = Icons.Sharp.Phone,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                            Text(text = "Gọi điện", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(3.dp))
                    Box(
                        modifier =
                        Modifier
                            .background(GHTKColor1)
                            .size(70.dp)
                            .padding(bottom = 8.dp),
                    ) {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Icon(
                                imageVector = Icons.Sharp.Phone,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                            Text(text = "Gọi điện", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(3.dp))
                    Box(
                        modifier =
                        Modifier
                            .background(GHTKColor1)
                            .size(70.dp)
                            .padding(bottom = 8.dp),
                    ) {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Icon(
                                imageVector = Icons.Sharp.Phone,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                            Text(text = "Gọi điện", color = Color.White)
                        }
                    }
                }

            }
        }
    }
}
