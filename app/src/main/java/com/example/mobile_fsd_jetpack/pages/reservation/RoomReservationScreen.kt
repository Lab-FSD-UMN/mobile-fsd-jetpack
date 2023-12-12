package com.example.mobile_fsd_jetpack.pages.reservation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.room.RoomsApiService
import com.example.mobile_fsd_jetpack.api.response_model.item.GetItemsApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomsApiResponse
import com.example.mobile_fsd_jetpack.models.Item
import com.example.mobile_fsd_jetpack.models.Room
import com.example.mobile_fsd_jetpack.navigation.ReservationRoutes
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.BiruMuda_Lightest
import com.example.mobile_fsd_jetpack.ui.theme.BiruUMN
import com.example.mobile_fsd_jetpack.ui.theme.ItemCard
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme
import com.example.mobile_fsd_jetpack.ui.theme.PageHeading
import com.example.mobile_fsd_jetpack.ui.theme.RoomCard
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

    LaunchedEffect(Unit){
        val call = getRoomsApiService.getRooms()

        call.enqueue(object : Callback<GetRoomsApiResponse> {
            override fun onResponse(call: Call<GetRoomsApiResponse>, response: Response<GetRoomsApiResponse>) {
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
            // SEARCH BAR
//            Box (
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(
//                        color = BiruMuda_Lightest,
//                        shape = RoundedCornerShape(size = 8.dp)
//                    )
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .padding(8.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = "Back",
//                        tint = BiruUMN
//                    )
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Text(
//                        text = "Search a lab room...",
//                        style = TextStyle(
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight(300),
//                            color = BiruUMN,
//                        )
//                    )
//                }
//
//            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = {
                            if (searchText == ""){
                                rooms = allRooms
                            } else {
                                rooms = allRooms.filter { it.name.contains(searchText, ignoreCase = true) }
                            }
                        }
                    ){
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray,
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = {
                            Text(
                                text = "Search a lab room...",
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                    )
                }
            }



            Spacer(modifier = Modifier.height(20.dp))

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
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CustomTextField() {
//    var text by remember { mutableStateOf("") }
//
//    TextField(
//        value = text,
//        onValueChange = {
//            text = it
//        },
//        modifier = Modifier
//            .fillMaxWidth(),
//        textStyle = TextStyle(
//            fontSize = 14.sp,
//            fontWeight = FontWeight(300),
//            color = BiruUMN,
//        ),
//        placeholder = {
//            Text("Search a lab room...") },
//    )
//}

@Preview
@Composable
fun RoomReservationPreview() {
    MobilefsdjetpackTheme {
        RoomReservationScreen()
    }
}