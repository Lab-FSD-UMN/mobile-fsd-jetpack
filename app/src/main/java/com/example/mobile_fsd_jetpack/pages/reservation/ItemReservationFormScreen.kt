package com.example.mobile_fsd_jetpack.pages.reservation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.PageHeading

@Composable
fun ItemReservationFormScreen(navController: NavController?= null, id : String?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        PageHeading("Item Reservation Form", navController)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .wrapContentSize(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text=id.toString())
        }
    }
}