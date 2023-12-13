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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.pages.monitoring.ItemMonitoringScreen
import com.example.mobile_fsd_jetpack.pages.monitoring.RoomMonitoringScreen
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.BiruUMN
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme

// nanti jangan lupa di delete
data class MonitoringData (
    // ROOM
    val roomName: String? = null,
    val roomCode: String? = null,

    // ITEM
    val itemName: String? = null,
    val qty: Int? = null,

    // GENERAL
    val category: String,
    val status: String,
    val reservationDate: String,
    val description: String? = null, // nanti dibikin jangan optional
    // val image: ?????
)


@Composable
fun MonitoringScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.TopCenter)
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
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
    }
}

@Composable
fun TabScreen() {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Room Reservation", "Item Reservation")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier.padding(bottom = 15.dp)
        ) {
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
//            1 -> ItemMonitoringScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MonitorPreview() {
    MobilefsdjetpackTheme {
        MonitoringScreen()
    }
}