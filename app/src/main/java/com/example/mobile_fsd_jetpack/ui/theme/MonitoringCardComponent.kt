package com.example.mobile_fsd_jetpack.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_fsd_jetpack.R
import com.example.mobile_fsd_jetpack.pages.MonitoringData

// Reference : https://developer.android.com/jetpack/compose/components/bottom-sheets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationCard(
    reservation: MonitoringData,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    // val coroutineScope = rememberCoroutineScope() // kalo ini mau dipake nantinya, perlu jadi parameter buat reservationDetailBottomSheet()
    var showBottomSheet by remember { mutableStateOf(false) }

    val isRoom: Boolean = reservation.category.lowercase() == "room"

    Card(
        onClick = { showBottomSheet = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
//        elevation = 4.dp // Convert Dp to CardElevation
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = if (isRoom)
                    "Room: ${reservation.roomName}"
                else
                    "Item: ${reservation.itemName}",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Text(
                text = "Status: ${reservation.status}",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = when (reservation.status) {
                        "approve" -> Green
                        "pending" -> Yellow
                        "decline" -> Red
                        else -> Color.Gray // Default color, you can adjust this based on your needs
                    }
                )
            )
            Text(
                text = "Reservation Date: ${reservation.reservationDate}",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            )
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = bottomSheetState,
            containerColor = BiruMuda_Lightest,
        ) {
            DetailBottomSheet(reservation, isRoom)
        }
    }
}

@Composable
fun DetailBottomSheet(reservation: MonitoringData, isRoom: Boolean) { // nanti tambahin parameter nilai
    Column (
        modifier = Modifier
            .background(BiruMuda_Lightest)
            .padding(
                start = 30.dp,
                end = 30.dp,
                top = 0.dp,
                bottom = 50.dp
            )
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.lab1),
                contentDescription = "Reserved Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text =
                if (isRoom) "${reservation.roomCode} - ${reservation.roomName}"
                else "${reservation.itemName}",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700),
                    color = BiruUMN,
                    textAlign = TextAlign.Center,
                )
            )
            Box(
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .height((0.5).dp)
                    .background(color = BiruMuda_Lighter)
            )

            if (!isRoom) TheSection(title = "Quantity", body = "${reservation.qty} pcs")

            TheSection(title = "Date", body = reservation.reservationDate)
            TheSection(title = "Time", body = "16.00 - 19.00")

            Spacer(modifier = Modifier.height(5.dp))
            TheSection(title = "Description", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = when (reservation.status) {
                            "approve" -> Green
                            "pending" -> Yellow
                            "decline" -> Red
                            else -> Color.Gray
                        }
                    )
                    .padding(0.dp, 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = when (reservation.status) {
                        "approve" -> "Approved"
                        "pending" -> "Pending"
                        "decline" -> "Declined"
                        else -> "Unknown"
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                        color = AlmostWhite,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun TheSection(title: String, body: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (title != "Description") {
            SectionTitle(text = title)
            SectionBody(text = body)
        }
        else {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(600),
                    color = BiruUMN,
                    textAlign = TextAlign.Center,
                )
            )
            SectionTitle(text = body)
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight(400),
            color = BiruMuda,
            textAlign = TextAlign.Center,
        )
    )
}

@Composable
fun SectionBody(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight(600),
            color = BiruUMN,
            textAlign = TextAlign.Center,
        )
    )
}


// HOW TO USE ONDISMISS()
//        Button(onClick = {
//            scope.launch { sheetState.hide() }.invokeOnCompletion {
//                if (!sheetState.isVisible) {
//                    showBottomSheet = false
//                }
//            }
//        }) {
//            Text("Hide bottom sheet")
//        }