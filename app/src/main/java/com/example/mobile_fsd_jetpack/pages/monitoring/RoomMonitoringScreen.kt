package com.example.mobile_fsd_jetpack.pages.monitoring

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.room.RoomsApiService
import com.example.mobile_fsd_jetpack.api.response_model.room.GetSelfRoomReservationApiResponse
import com.example.mobile_fsd_jetpack.auth.UserAuth
import com.example.mobile_fsd_jetpack.models.RoomReservationData
import com.example.mobile_fsd_jetpack.ui.theme.LoadingScreen
import com.example.mobile_fsd_jetpack.ui.theme.NotGoingWellDisplay
import com.example.mobile_fsd_jetpack.ui.theme.NotGoingWellTypes
import com.example.mobile_fsd_jetpack.ui.theme.ReservationCard
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

@Composable
fun RoomMonitoringScreen(navController: NavController) {

    val context = LocalContext.current
    var roomReservations by remember { mutableStateOf<List<RoomReservationData?>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit){

        val userToken = UserAuth(context).getToken()

        val retrofit = BaseAPIBuilder().retrofit
        val getItemsApiService = retrofit.create(RoomsApiService::class.java)

        val call = getItemsApiService.getSelfReservation("Bearer $userToken")
        call.enqueue(object : Callback<GetSelfRoomReservationApiResponse> {
            override fun onResponse(call: Call<GetSelfRoomReservationApiResponse>, response: Response<GetSelfRoomReservationApiResponse>) {
                Log.d("message", response.body()?.message.toString())
//                Log.d("data", response.body()?.data.
                var data = response.body()?.data
                if (data != null) {
                    Log.d("data", data[0].toString())
                    roomReservations = data
                }
                isLoading = false
            }

            override fun onFailure(call: Call<GetSelfRoomReservationApiResponse>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
                isLoading = false
                // nanti mungkin bisa tambahin error handler disini
            }
        })
//        Log.d("items", roomReservations.toString())
    }

    Column {
        when {
            isLoading -> LoadingScreen()
            roomReservations.isNotEmpty() -> RoomReservationList(roomReservations)
            else -> NotGoingWellDisplay(navController = navController, type = NotGoingWellTypes.NoReservation)
        }
    }
}

@Composable
fun RoomReservationList(reservations : List<RoomReservationData?>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(reservations) { _, reservation ->
            ReservationCard(roomReservation = reservation)
        }
    }
}
