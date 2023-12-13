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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mobile_fsd_jetpack.BuildConfig
import com.example.mobile_fsd_jetpack.R
import com.example.mobile_fsd_jetpack.models.ItemReservationData
import com.example.mobile_fsd_jetpack.models.RoomReservationData
import com.example.mobile_fsd_jetpack.navigation.MainNavRoutes
import com.example.mobile_fsd_jetpack.pages.monitoring.DateTime
import com.example.mobile_fsd_jetpack.pages.monitoring.GeneralMonitoringData
import com.example.mobile_fsd_jetpack.pages.monitoring.formatDateTime
import com.example.mobile_fsd_jetpack.pages.monitoring.formatStatus
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

// Reference : https://developer.android.com/jetpack/compose/components/bottom-sheets

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
    var data = GeneralMonitoringData(formatStatus(55), DateTime("Monday", "16.00"),  DateTime("Monday", "18.00"), "")

    // all general attribute can be assigned here
    if (roomReservation != null) {
        data = GeneralMonitoringData(
            status = formatStatus(roomReservation.status),
            reservationStart = formatDateTime(roomReservation.reservation_start_time),
            reservationEnd = formatDateTime(roomReservation.reservation_end_time),
            note = roomReservation.note
        )
    }

    if (itemReservation != null) {
        data = GeneralMonitoringData(
            status = formatStatus(itemReservation.status),
            reservationStart = formatDateTime(itemReservation.reservation_start_time),
            reservationEnd = formatDateTime(itemReservation.reservation_end_time),
            note = itemReservation.note
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
                    "${roomReservation?.room?.location} - ${roomReservation?.room?.name}"
                else
                    "${itemReservation?.item?.name}",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Text(
                text = "Status: ${data.status.text}" ,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = data.status.color,
                )
            )
            Text(
                text = "Reservation Date: ${data.reservationStart.date}",
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

    val API_URL = BuildConfig.API_URL

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
                painter = rememberImagePainter(
                    data =
                        if (isRoom) "${API_URL}${roomReservation?.room?.image}"
                        else "${API_URL}${itemReservation?.item?.image}",
                    builder = {
                        crossfade(true)
                        placeholder(android.R.drawable.ic_menu_gallery)
                    }
                ),
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

            TheSection(title = "Date", body = data.reservationStart.date)
            TheSection(title = "Time", body = "${data.reservationStart.time} - ${data.reservationEnd.time}")

            Spacer(modifier = Modifier.height(5.dp))
            TheSection(title = "Description", body = "${data.note}")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = data.status.color
                    )
                    .padding(0.dp, 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = data.status.text,
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
fun NoReservation(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ){
        Text(
            text = "No reservations yet",
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight(700),
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
        )
        Text(
            text = "Make your first reservation",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = Color(0x99000000),
                textAlign = TextAlign.Center,
            )
        )
        Image(
            painter = painterResource(id = R.drawable.empty_box),
            contentDescription = "empty reservation",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(0.dp, 30.dp)
                .width(240.dp)
        )
        Button(
            onClick = { navController.navigate(MainNavRoutes.Reservation.route) },
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = Color(0xFF006BBD),
                    contentColor = AlmostWhite
                )
        ) {
            Text(
                text = "Reserve Now",
                style = MaterialTheme.typography.labelMedium,
                fontSize = 18.sp,
                modifier = Modifier.padding(24.dp, 5.dp)
            )
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