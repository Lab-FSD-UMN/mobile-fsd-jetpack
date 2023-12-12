package com.example.mobile_fsd_jetpack.pages.monitoring

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mobile_fsd_jetpack.pages.MonitoringData
import com.example.mobile_fsd_jetpack.ui.theme.ReservationCard

@Composable
fun RoomMonitoringScreen() {
    val roomReservations = getDummyRoomReservations() // Replace this with your actual data

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(roomReservations) { _, reservation ->
            ReservationCard(reservation, onDismiss = {})
        }
    }
}

fun getDummyRoomReservations(): List<MonitoringData> {
    // nanti bisa pake kea map aja gitu (?)
    // Replace this with your actual data retrieval logic
    return listOf(
        // room sama item data classnya digabung (untuk tampilin aja), tapi ada yang optional
        MonitoringData(category = "room", roomName = "Illustration and Publishing Laboratory" , roomCode = "C101", status = "approve", reservationDate = "Mon, 12 December 2023"),
        MonitoringData(category = "room", roomName = "Cinema Room" , roomCode = "C601", status = "pending", reservationDate = "Fri, 21 December 2023"),
        MonitoringData(category = "room", roomName = "Stop Motion & Render Form Studio" , roomCode = "D903", status = "decline", reservationDate = "Wed, 3 December 2023")

        // Add more dummy data as needed
    )
}