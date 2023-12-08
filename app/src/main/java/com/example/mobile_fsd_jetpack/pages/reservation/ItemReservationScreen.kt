package com.example.mobile_fsd_jetpack.pages.reservation

import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.Coil
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mobile_fsd_jetpack.BuildConfig
import com.example.mobile_fsd_jetpack.R
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.item.ItemsApiService
import com.example.mobile_fsd_jetpack.api.response_model.item.GetItemsApiResponse
import com.example.mobile_fsd_jetpack.models.Item
import com.example.mobile_fsd_jetpack.navigation.ReservationRoutes
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.BiruMuda_Lightest
import com.example.mobile_fsd_jetpack.ui.theme.BiruUMN
import com.example.mobile_fsd_jetpack.ui.theme.ItemCard
import com.example.mobile_fsd_jetpack.ui.theme.PageHeading
import com.example.mobile_fsd_jetpack.ui.theme.PrimaryTextButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ItemReservationScreen(navController: NavController?= null) {

    val context = LocalContext.current

    var items by remember { mutableStateOf<List<Item>>(emptyList()) }

    val retrofit = BaseAPIBuilder().retrofit
    val getItemsApiService = retrofit.create(ItemsApiService::class.java)
    val call = getItemsApiService.getItems()

    call.enqueue(object : Callback<GetItemsApiResponse> {
        override fun onResponse(call: Call<GetItemsApiResponse>, response: Response<GetItemsApiResponse>) {
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d("t", responseBody.toString())
                responseBody?.items?.let {
                    itemsList -> items = itemsList
                }

            } else {
                Log.d("e", response.message())
            }
        }

        override fun onFailure(call: Call<GetItemsApiResponse>, t: Throwable) {
            Log.d("onFailure", t.message.toString())
        }
    })

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
            // SEARCH BAR
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = BiruMuda_Lightest,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Back",
                        tint = BiruUMN
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Search a lab room...",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(300),
                            color = BiruUMN,
                        )
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
                items(items) { item ->
                    ItemCard(
                        route = ReservationRoutes.ItemReservationForm.route,
                        navController = navController,
                        context = context,
                        item = item,
                    )
                }
            }

        }

    }
}