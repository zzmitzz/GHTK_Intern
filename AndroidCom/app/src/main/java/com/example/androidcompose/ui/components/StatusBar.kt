package com.example.androidcompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcompose.ui.components.status.BoughtShop
import com.example.androidcompose.ui.components.status.FireWallet
import com.example.androidcompose.ui.components.status.Orders
import com.example.androidcompose.ui.components.status.PeriodBuying
import com.example.androidcompose.ui.components.status.ReceiveOrderSpeed
import com.example.androidcompose.ui.components.status.Review
import com.example.androidcompose.ui.components.status.Success
import com.example.androidcompose.ui.components.status.VisitedShop
import com.example.androidcompose.ui.theme.AndroidComposeTheme


@Composable
fun StatusBar() {
    Column(verticalArrangement = Arrangement.SpaceBetween, modifier =  Modifier.padding(start = 8.dp, end = 8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            FireWallet(a = 1)
            Divider(modifier = Modifier.height(19.dp).width(2.dp).align(Alignment.CenterVertically))
            Orders(a = 1)
            Divider(modifier = Modifier.height(19.dp).width(2.dp).align(Alignment.CenterVertically))
            Success(a = 1)
            Divider(modifier = Modifier.height(19.dp).width(2.dp).align(Alignment.CenterVertically))
            ReceiveOrderSpeed()
        }
        Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Review(like = 1, disLike = 1)
            Divider(modifier = Modifier.height(19.dp).width(2.dp).align(Alignment.CenterVertically))
            VisitedShop(a = 1)
            Divider(modifier = Modifier.height(19.dp).width(2.dp).align(Alignment.CenterVertically))
            BoughtShop(a = 1)
            Divider(modifier = Modifier.height(19.dp).width(2.dp).align(Alignment.CenterVertically))
            PeriodBuying(a = 1)
        }
    }
}
