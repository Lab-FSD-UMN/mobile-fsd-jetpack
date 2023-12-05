package com.example.mobile_fsd_jetpack.pages.reservation

import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.Coil
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mobile_fsd_jetpack.R
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.item.GetItemsApiService
import com.example.mobile_fsd_jetpack.api.response_model.item.GetItemsApiResponse
import com.example.mobile_fsd_jetpack.models.Item
import com.example.mobile_fsd_jetpack.navigation.ReservationRoutes
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.PrimaryTextButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ItemReservationScreen(navController: NavController?= null) {

    val context = LocalContext.current

    var items by remember { mutableStateOf<List<Item>>(emptyList()) }

    val retrofit = BaseAPIBuilder().retrofit
    val getItemsApiService = retrofit.create(GetItemsApiService::class.java)
    val call = getItemsApiService.getItems()

    call.enqueue(object : Callback<GetItemsApiResponse> {
        override fun onResponse(call: Call<GetItemsApiResponse>, response: Response<GetItemsApiResponse>) {
            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.items?.let {
                    itemsList -> items = itemsList
                }

            } else {

            }
        }

        override fun onFailure(call: Call<GetItemsApiResponse>, t: Throwable) {
            Log.d("onFailure", t.message.toString())
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.Center)
            .padding(20.dp)
    ) {
        Text(
            text = "Item Reservation",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
        PrimaryTextButton(
            text = "Go to Item Reservation Form",
            onClick = {},
            navController = navController,
            route = ReservationRoutes.ItemReservationForm.route,
            modifier = Modifier
                .padding(start = 24.dp, top = 12.dp, end = 24.dp, bottom = 12.dp)
        )

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(items) { item ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(Color.Gray)
                ) {
                    item.image?.let { imageUrl ->
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data("https://ba13-2001-448a-2042-3e13-a80d-7091-665e-de02.ngrok-free.app" + imageUrl)
                                .crossfade(true)
                                .build(),
                            placeholder = ColorPainter(Color.Transparent),
                            contentDescription = stringResource(R.string.item_description, item.name),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.3f)
                            .background(Color(android.graphics.Color.parseColor("#0F9ED8")))
                            .align(Alignment.BottomStart)
                            .zIndex(1f)
                    ){
                        Text(
                            text = item.name,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize()
                                .background(Color.Transparent)
                                .align(Alignment.BottomStart),
                            style = TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }


                }
            }
        }


    }
}