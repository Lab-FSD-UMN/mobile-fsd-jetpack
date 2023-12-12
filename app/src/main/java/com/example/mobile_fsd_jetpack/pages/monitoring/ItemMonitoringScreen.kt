package com.example.mobile_fsd_jetpack.pages.monitoring

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mobile_fsd_jetpack.pages.MonitoringData
import com.example.mobile_fsd_jetpack.ui.theme.ReservationCard

@Composable
fun ItemMonitoringScreen() {
    val itemReservations = getDummyItemReservations() // Replace this with your actual data

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(itemReservations) { _, reservation ->
            ReservationCard(reservation, onDismiss = {})
            Log.d("CONSOLE", reservation.toString())
        }
    }
}

fun getDummyItemReservations(): List<MonitoringData> {
    // nanti bisa pake kea map aja gitu (?)
    // Replace this with your actual data retrieval logic
    return listOf(
        MonitoringData(category = "item", itemName = "Kamera Nikon", qty = 2, status = "approve", reservationDate = "2023-12-10"),
        MonitoringData(category = "item", itemName = "Clapper Board", qty = 1, status = "pending", reservationDate = "2023-12-10"),
        MonitoringData(category = "item", itemName = "Steadycam", qty = 1, status = "decline", reservationDate = "2023-12-12"),
        // Add more dummy data as needed
    )
}