package com.example.mobile_fsd_jetpack.pages.reservation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.navigation.ReservationRoutes
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.PrimaryTextButton

@Composable
fun RoomReservationScreen(navController: NavController?= null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.Center)
            .padding(20.dp)
    ) {
        Text(
            text = "Room Reservation",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
        PrimaryTextButton(
            text = "Go to Room Reservation Form",
            onClick = {},
            navController = navController,
            route = ReservationRoutes.RoomReservationForm.route,
            modifier = Modifier
                .padding(start = 24.dp, top = 12.dp, end = 24.dp, bottom = 12.dp)
        )
    }
}