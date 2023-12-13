package com.example.mobile_fsd_jetpack.pages.monitoring

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.item.ItemsApiService
import com.example.mobile_fsd_jetpack.api.response_model.item.GetSelfItemReservationApiResponse
import com.example.mobile_fsd_jetpack.auth.UserAuth
import com.example.mobile_fsd_jetpack.models.ItemReservationData
import com.example.mobile_fsd_jetpack.ui.theme.ReservationCard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ItemMonitoringScreen() {

    val context = LocalContext.current
    var itemReservations by remember { mutableStateOf<List<ItemReservationData?>>(emptyList()) }

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
//                    Log.w("e", response.message())
                }
            }

            override fun onFailure(call: Call<GetSelfItemReservationApiResponse>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
//        Log.d("items", itemReservations.toString())
    }

    Column {
        ItemReservationList(itemReservations)
    }
}

@Composable
fun ItemReservationList(reservations : List<ItemReservationData?>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(reservations) { _, reservation ->
            if (reservation != null) {
                ReservationCard(itemReservation = reservation)
            }
        }
    }
}
