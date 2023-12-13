package com.example.mobile_fsd_jetpack.pages.monitoring

//@Composable
//fun RoomMonitoringScreen() {
//    val roomReservations = getDummyRoomReservations() // Replace this with your actual data
//=======
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.room.RoomsApiService
import com.example.mobile_fsd_jetpack.api.response_model.room.GetSelfRoomReservationApiResponse
import com.example.mobile_fsd_jetpack.auth.UserAuth
import com.example.mobile_fsd_jetpack.models.RoomReservationData
import com.example.mobile_fsd_jetpack.pages.MonitoringData
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

@Composable
fun RoomMonitoringScreen() {

    val context = LocalContext.current
    var roomReservations by remember { mutableStateOf<List<RoomReservationData?>>(emptyList()) }

    LaunchedEffect(Unit){

        val userToken = UserAuth(context).getToken()

        val retrofit = BaseAPIBuilder().retrofit
        val getItemsApiService = retrofit.create(RoomsApiService::class.java)

        val call = getItemsApiService.getSelfReservation("Bearer $userToken")
        call.enqueue(object : Callback<GetSelfRoomReservationApiResponse> {
            override fun onResponse(call: Call<GetSelfRoomReservationApiResponse>, response: Response<GetSelfRoomReservationApiResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.userReservation?.let { data ->
                        roomReservations = data
                        Log.d("CONSOLE", data.toString())
                    }

                } else {
                    Log.d("CONSOLE", response.message())
                }
            }

            override fun onFailure(call: Call<GetSelfRoomReservationApiResponse>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
        Log.d("items", roomReservations.toString())
    }

    Column {
        Text(text = "Udh integrasi AIR")
        RoomReservationList(roomReservations)
    }
}

@Composable
fun RoomReservationList(reservations : List<RoomReservationData?>) {
//>>>>>>> a41c7b084c329e399bd0f031d77fe2e486dce7a0

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
//<<<<<<< HEAD
//    ) {
//        itemsIndexed(roomReservations) { _, reservation ->
//            ReservationCard(reservation)
//=======
            .padding(16.dp)
    ) {
        itemsIndexed(reservations) { _, reservation ->
            RoomReservationCard(reservation)
        }
    }
}

@Composable
fun RoomReservationCard(reservation: RoomReservationData?) {
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
                text = "Room: ${reservation?.room?.name}",
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