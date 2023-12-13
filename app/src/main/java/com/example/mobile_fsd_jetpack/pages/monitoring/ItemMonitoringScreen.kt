package com.example.mobile_fsd_jetpack.pages.monitoring

//@Composable
//fun ItemMonitoringScreen() {
//    val itemReservations = getDummyItemReservations() // Replace this with your actual data

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.item.ItemsApiService
import com.example.mobile_fsd_jetpack.api.response_model.item.GetItemByIDApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.item.GetSelfItemReservationApiResponse
import com.example.mobile_fsd_jetpack.auth.UserAuth
import com.example.mobile_fsd_jetpack.models.ItemReservationData
import com.example.mobile_fsd_jetpack.models.Room
import com.example.mobile_fsd_jetpack.pages.MonitoringData
import com.example.mobile_fsd_jetpack.ui.theme.ReservationCard
//import com.example.mobile_fsd_jetpack.pages.RoomReservation
//import com.example.mobile_fsd_jetpack.pages.getDummyRoomReservations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ItemMonitoringScreen() {

    val context = LocalContext.current
    var itemReservations by remember { mutableStateOf<List<ItemReservationData?>>(emptyList()) }
    // var monitorItemReservations by remember { mutableStateOf<List<MonitoringData?>>(emptyList()) }

    LaunchedEffect(Unit){

        val userToken = UserAuth(context).getToken()

        val retrofit = BaseAPIBuilder().retrofit
        val getItemsApiService = retrofit.create(ItemsApiService::class.java)

        val call = getItemsApiService.getSelfReservation("Bearer $userToken")
        call.enqueue(object : Callback<GetSelfItemReservationApiResponse> {
            override fun onResponse(call: Call<GetSelfItemReservationApiResponse>, response: Response<GetSelfItemReservationApiResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.userReservation?.let { data ->
                        itemReservations = data
//                        Log.d("CONSOLE", responseBody.toString())
                    }

                } else {
//                    Log.d("e", response.message())
                }
            }

            override fun onFailure(call: Call<GetSelfItemReservationApiResponse>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
//        Log.d("items", itemReservations.toString())
    }

    Column {
        Text(text = "Udh integrasi AIR")
        ItemReservationList(itemReservations)
    }
}

@Composable
fun ItemReservationList(reservations : List<ItemReservationData?>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        itemsIndexed(reservations) { _, reservation ->
            if (reservation != null) {
                ReservationCard(itemReservation = reservation)
            }
//            ItemReservationCard(reservation)
        }
    }
}

@Composable
fun ItemReservationCard(reservation: ItemReservationData?) {
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
                text = "Room: ${reservation?.item?.name}",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Text(
                text = when (reservation?.status) {
                    1 -> "Accepted"
                    0 -> "Pending"
                    2 -> "Rejected"
                    else -> "-" // Default color, you can adjust this based on your needs
                },
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = when (reservation?.status) {
                        1 -> Color.Green
                        0 -> Color.Yellow
                        2 -> Color.Red
                        else -> Color.Black // Default color, you can adjust this based on your needs
                    }
                )
            )
            Text(
                text = "Reservation Date: ${reservation?.reservation_start_time}",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            )
//>>>>>>> a41c7b084c329e399bd0f031d77fe2e486dce7a0
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