package com.example.androidcompose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcompose.R
import com.example.androidcompose.ui.theme.AndroidComposeTheme
import com.example.androidcompose.ui.theme.GHTKColor1

@Composable
fun UserInformation(modifier: Modifier = Modifier) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp),
    ) {
        Image(
            modifier = Modifier.align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.arrow_back_ios_24dp_000000_fill1_wght400_grad0_opsz24),
            contentDescription = null,
        )
        Box(
            modifier =
            Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(GHTKColor1),
        ) {
            Text(
                text = "A",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 20.sp,
            )
        }
        Column(
            modifier =
            Modifier
                .padding(start = 5.dp)
                .align(Alignment.CenterVertically),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "843***4455",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.weight(1f))
                Card(
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .background(GHTKColor1)
                            .padding(2.dp)
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Sharp.FavoriteBorder,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(end = 5.dp),
                            )
                            Text(text = "Theo dõi", color = Color.White, fontSize = 15.sp)
                        }
                    }
                }
            }
            Row() {
                Image(painter = painterResource(id = R.drawable.done_all_24dp_000000_fill1_wght400_grad0_opsz24), contentDescription =null)
                Text(text = "Đã xãc thực SĐT & Địa chỉ", color = Color.Black, fontSize = 14.sp)
            }
        }
    }
}

