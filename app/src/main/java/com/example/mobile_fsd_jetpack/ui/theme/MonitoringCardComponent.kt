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
import com.example.mobile_fsd_jetpack.models.ItemReservationData
import com.example.mobile_fsd_jetpack.models.Room
import com.example.mobile_fsd_jetpack.models.RoomReservationData
import com.example.mobile_fsd_jetpack.pages.MonitoringData

// Reference : https://developer.android.com/jetpack/compose/components/bottom-sheets

class GeneralMonitoringData (
    val statusText : String,
    val statusColor: Color,
    val reservationStartTime : String,
    val reservationEndTime : String,
    val note : String?,
    val createdAt : String,
    val updatedAt : String,
)

fun statusText(status: Int) : String {
    val formattedStatus = when (status) {
        1 -> "Approved"
        0 -> "Pending"
        2 -> "Rejected"
        else -> "-"
    }

    return formattedStatus
}

fun statusColor(status: Int) : Color {
    val formattedStatus = when (status) {
        1 -> Green
        0 -> Yellow
        2 -> Red
        else -> Color.Gray
    }

    return formattedStatus
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationCard(
    roomReservation: RoomReservationData? = null,
    itemReservation: ItemReservationData? = null,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    // val coroutineScope = rememberCoroutineScope() // kalo ini mau dipake nantinya, perlu jadi parameter buat reservationDetailBottomSheet()
    var showBottomSheet by remember { mutableStateOf(false) }

    // If itemReservation == null, then it is a room
    val isRoom: Boolean = itemReservation == null
    var data: GeneralMonitoringData = GeneralMonitoringData("", Color.Gray, "", "", "", "", "")

    // all general attribute can be assigned here
    if (roomReservation != null) {
        data = GeneralMonitoringData(
            statusText = statusText(roomReservation.status),
            statusColor = statusColor(roomReservation.status),
            reservationStartTime = roomReservation.reservation_start_time,
            reservationEndTime = roomReservation.reservation_end_time,
            note = roomReservation?.note,
            createdAt = roomReservation.created_at,
            updatedAt = roomReservation.updated_at
        )
    }

    if (itemReservation != null) {
        data = GeneralMonitoringData(
            statusText = statusText(itemReservation.status),
            statusColor = statusColor(itemReservation.status),
            reservationStartTime = itemReservation.reservation_start_time,
            reservationEndTime = itemReservation.reservation_end_time,
            note = itemReservation?.note,
            createdAt = itemReservation.created_at,
            updatedAt = itemReservation.updated_at
        )
    }

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
                    "Room: ${roomReservation?.room?.name}"
                else
                    "Item: ${itemReservation?.item?.name}",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Text(
                text = "Status: ${data.statusText}" ,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = data.statusColor,
                )
            )
            Text(
                text = "Reservation Date: ${data.reservationStartTime}",
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
            if(isRoom)
                DetailBottomSheet(
                    roomReservation = roomReservation,
                    data = data,
                    isRoom = true)
            else
                DetailBottomSheet(
                    itemReservation = itemReservation,
                    data = data,
                    isRoom = false)
        }
    }
}

@Composable
fun DetailBottomSheet(
    roomReservation: RoomReservationData ?= null,
    itemReservation: ItemReservationData ?= null,
    data: GeneralMonitoringData,
    isRoom: Boolean
) {
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
                if (isRoom) "${roomReservation?.room?.location} - ${roomReservation?.room?.name}"
                else "${itemReservation?.item?.name}",
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

            if (!isRoom) TheSection(title = "Quantity", body = "${itemReservation?.quantity} pcs")

            TheSection(title = "Date", body = "${data?.reservationStartTime}")
//            TheSection(title = "Time", body = roomReservation!!.)

            Spacer(modifier = Modifier.height(5.dp))
//            roomReservation?.note?.let { TheSection(title = "Description", body = it) }

            TheSection(title = "Description", body = "${data?.note}")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = data.statusColor
                    )
                    .padding(0.dp, 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = data.statusText,
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