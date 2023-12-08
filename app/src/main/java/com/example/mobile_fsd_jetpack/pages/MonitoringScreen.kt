package com.example.mobile_fsd_jetpack.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.pages.monitoring.ItemMonitoringScreen
import com.example.mobile_fsd_jetpack.pages.monitoring.RoomMonitoringScreen
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.BiruUMN
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme
import com.example.mobile_fsd_jetpack.ui.theme.PageHeading


@Composable
fun MonitoringScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.TopCenter)
            .padding(20.dp)
    ) {
        Text(
            text = "My Reservations",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(700),
                color = BiruUMN,
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        TabScreen()
        // Dummy Data
        RoomReservationList()
    }
}

@Composable
fun RoomReservationList() {
    val roomReservations = getDummyRoomReservations() // Replace this with your actual data

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        itemsIndexed(roomReservations) { _, reservation ->
            RoomReservationCard(reservation)
        }
    }
}

@Composable
fun RoomReservationCard(reservation: RoomReservation) {
    Card(
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
                text = "Room: ${reservation.roomName}",
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
                        "approve" -> Color.Green
                        "pending" -> Color.Yellow
                        "decline" -> Color.Red
                        else -> Color.Black // Default color, you can adjust this based on your needs
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
}

data class RoomReservation(
    val roomName: String,
    val status: String,
    val reservationDate: String
)

fun getDummyRoomReservations(): List<RoomReservation> {
    // Replace this with your actual data retrieval logic
    return listOf(
        RoomReservation("Room 101", "approve", "2023-12-10"),
        RoomReservation("Room 101", "pending", "2023-12-10"),
        RoomReservation("Room 102", "decline", "2023-12-12"),
        // Add more dummy data as needed
    )
}


@Composable
fun TabScreen() {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Room Reservation", "Item Reservation")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                val isSelected = tabIndex == index

                // Custom appearance
                val bgColor = if(isSelected) BiruUMN else AlmostWhite
                val contentColor = if(isSelected) AlmostWhite else BiruUMN

                Tab(
                    selected = isSelected,
                    text = { Text(
                        title,
                        color = contentColor
                    ) },
                    onClick = { tabIndex = index },
                    modifier = Modifier
                        .background(
                            color = bgColor,
                            shape = RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp)
                        )
                )
            }
        }
        when (tabIndex) {
            0 -> RoomMonitoringScreen()
            1 -> ItemMonitoringScreen()
        }
    }
}

@Composable
@UiComposable
fun TabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Blue,
    contentColor: Color = contentColorFor(backgroundColor),
    indicator: @Composable @UiComposable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        TabRowDefaults.Indicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
        )
    },
//    divider: @Composable @UiComposable () -> Unit = @Composable {
//        TabRowDefaults.Divider()
//    },
    tabs: @Composable @UiComposable () -> Unit
): Unit{}

@Preview(showBackground = true)
@Composable
fun MonitorPreview() {
    MobilefsdjetpackTheme {
        MonitoringScreen()
    }
}