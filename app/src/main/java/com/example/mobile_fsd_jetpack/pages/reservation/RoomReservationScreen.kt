package com.example.mobile_fsd_jetpack.pages.reservation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.room.RoomsApiService
import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomsApiResponse
import com.example.mobile_fsd_jetpack.models.Room
import com.example.mobile_fsd_jetpack.navigation.ReservationRoutes
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.LoadingScreen
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme
import com.example.mobile_fsd_jetpack.ui.theme.PageHeading
import com.example.mobile_fsd_jetpack.ui.theme.RoomCard
import com.example.mobile_fsd_jetpack.ui.theme.SearchBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun RoomReservationScreen(navController: NavController?= null) { // nanti ?= null nya ilangin
    val context = LocalContext.current

    var rooms by remember { mutableStateOf<List<Room>>(emptyList()) }
    var allRooms by remember { mutableStateOf<List<Room>>(emptyList()) }
    var searchText by remember { mutableStateOf("") }

    val retrofit = BaseAPIBuilder().retrofit
    val getRoomsApiService = retrofit.create(RoomsApiService::class.java)

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        val call = getRoomsApiService.getRooms()
        isLoading = true
        call.enqueue(object : Callback<GetRoomsApiResponse> {

            override fun onResponse(call: Call<GetRoomsApiResponse>, response: Response<GetRoomsApiResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("t", responseBody.toString())
                    responseBody?.data?.rooms?.let {
                            roomList ->
                                rooms = roomList
                                allRooms = roomList
                    }
                } else {
                    Log.d("e", response.message())
                }
            }
            override fun onFailure(call: Call<GetRoomsApiResponse>, t: Throwable) {
                isLoading = false
                Log.d("onFailure", t.message.toString())
            }
        })
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        PageHeading("Room Reservation", navController)
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .wrapContentSize(Alignment.TopCenter)
//                .verticalScroll(rememberScrollState())
        ) {
            SearchBar(
                placeholder = "Search a lab room...",
                searchText = searchText,
                onSearchTextChanged = {newText -> searchText = newText},
                onClick = {
                    if (searchText == ""){
                        rooms = allRooms
                    } else {
                        rooms = allRooms.filter { it.name.contains(searchText, ignoreCase = true) }
                    }
                }
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
            ) {
                items(rooms) { room ->
                    RoomCard(
                        route = "${ReservationRoutes.RoomReservationForm.route}/${room.id}",
                        navController = navController,
                        context = context,
                        room = room,
                    )
                }
            }


            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}

@Preview
@Composable
fun RoomReservationPreview() {
    MobilefsdjetpackTheme {
        RoomReservationScreen()
    }
}