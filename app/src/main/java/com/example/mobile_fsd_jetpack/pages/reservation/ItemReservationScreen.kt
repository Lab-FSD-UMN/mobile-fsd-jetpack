package com.example.mobile_fsd_jetpack.pages.reservation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.item.ItemsApiService
import com.example.mobile_fsd_jetpack.api.response_model.item.GetItemsApiResponse
import com.example.mobile_fsd_jetpack.models.Item
import com.example.mobile_fsd_jetpack.navigation.ReservationRoutes
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.ItemCard
import com.example.mobile_fsd_jetpack.ui.theme.PageHeading
import com.example.mobile_fsd_jetpack.ui.theme.SearchBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ItemReservationScreen(navController: NavController?= null) {
    val context = LocalContext.current

    var items by remember { mutableStateOf<List<Item>>(emptyList()) }
    var allItems by remember { mutableStateOf<List<Item>>(emptyList()) }
    var searchText by remember { mutableStateOf("") }

    val retrofit = BaseAPIBuilder().retrofit
    val getItemsApiService = retrofit.create(ItemsApiService::class.java)

    LaunchedEffect(Unit){
        val call = getItemsApiService.getItems()
        call.enqueue(object : Callback<GetItemsApiResponse> {
            override fun onResponse(call: Call<GetItemsApiResponse>, response: Response<GetItemsApiResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("t", responseBody.toString())
                    responseBody?.items?.let {
                            itemsList ->
                                items = itemsList
                                allItems = itemsList
                    }

                } else {
                    Log.d("e", response.message())
                }
            }

            override fun onFailure(call: Call<GetItemsApiResponse>, t: Throwable) {
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
//            .verticalScroll(rememberScrollState())
    ) {
        PageHeading("Item Reservation", navController)

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .wrapContentSize(Alignment.TopCenter)
//                .verticalScroll(rememberScrollState())
        ){
            SearchBar(
                placeholder = "Search item...",
                searchText = searchText,
                onSearchTextChanged = {newText -> searchText = newText},
                onClick = {
                    if (searchText == ""){
                        items = allItems
                    } else {
                        items = allItems.filter { it.name.contains(searchText, ignoreCase = true) }
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
                items(items) { item ->
                    ItemCard(
                        route = "${ReservationRoutes.ItemReservationForm.route}/${item.id}",
                        navController = navController,
                        context = context,
                        item = item,
                    )
                }
            }

        }

    }
}