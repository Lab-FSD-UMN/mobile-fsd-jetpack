package com.example.mobile_fsd_jetpack.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile_fsd_jetpack.R
import com.example.mobile_fsd_jetpack.navigation.ReservationRoutes
import com.example.mobile_fsd_jetpack.pages.reservation.ItemReservationScreen
import com.example.mobile_fsd_jetpack.ui.theme.Background
import com.example.mobile_fsd_jetpack.ui.theme.ButtonImage
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme

@Composable
fun ReservationScreen(navController: NavController?= null) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
            .wrapContentSize(Alignment.Center)
            .padding(20.dp)
    ) {
        Text(
            text = "Reservation as Home Screen",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
        ButtonImage(
            text = "Room Reservation",
            image = R.drawable.room_reservation,
            onClick = {},
            route = ReservationRoutes.RoomReservation.route,
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(10.dp))

        ButtonImage(
            text = "Item Reservation",
            image = R.drawable.room_reservation,
            onClick = {},
            route = ReservationRoutes.ItemReservation.route,
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReservationPreview() {
    MobilefsdjetpackTheme {
        ReservationScreen()
    }
}