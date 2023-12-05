package com.example.mobile_fsd_jetpack.pages.reservation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.navigation.ReservationRoutes
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.BiruMuda_Lightest
import com.example.mobile_fsd_jetpack.ui.theme.BiruUMN
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme
import com.example.mobile_fsd_jetpack.ui.theme.PageHeading
import com.example.mobile_fsd_jetpack.ui.theme.RoomCard


@Composable
fun RoomReservationScreen(navController: NavController?= null) { // nanti ?= null nya ilangin
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        PageHeading("Room Reservation", navController)
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .wrapContentSize(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
        ) {
            // SEARCH BAR
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = BiruMuda_Lightest,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Back",
                        tint = BiruUMN
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Search a lab room...",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(300),
                            color = BiruUMN,
                        )
                    )
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                RoomCard(navController = navController, route = ReservationRoutes.RoomReservationForm.route)
                RoomCard(navController = navController, route = ReservationRoutes.RoomReservationForm.route)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                RoomCard(navController = navController, route = ReservationRoutes.RoomReservationForm.route)
                RoomCard(navController = navController, route = ReservationRoutes.RoomReservationForm.route)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                RoomCard(navController = navController, route = ReservationRoutes.RoomReservationForm.route)
                RoomCard(navController = navController, route = ReservationRoutes.RoomReservationForm.route)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                RoomCard(navController = navController, route = ReservationRoutes.RoomReservationForm.route)
                RoomCard(navController = navController, route = ReservationRoutes.RoomReservationForm.route)
            }
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CustomTextField() {
//    var text by remember { mutableStateOf("") }
//
//    TextField(
//        value = text,
//        onValueChange = {
//            text = it
//        },
//        modifier = Modifier
//            .fillMaxWidth(),
//        textStyle = TextStyle(
//            fontSize = 14.sp,
//            fontWeight = FontWeight(300),
//            color = BiruUMN,
//        ),
//        placeholder = {
//            Text("Search a lab room...") },
//    )
//}

@Preview
@Composable
fun RoomReservationPreview() {
    MobilefsdjetpackTheme {
        RoomReservationScreen()
    }
}