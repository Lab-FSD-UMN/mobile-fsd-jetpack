package com.example.mobile_fsd_jetpack.ui.theme

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mobile_fsd_jetpack.BuildConfig
import com.example.mobile_fsd_jetpack.R
import com.example.mobile_fsd_jetpack.models.Item
import com.example.mobile_fsd_jetpack.models.Room

// JANGAN LUPA BIKIN VERSI UNAVAILABLE (gabisa dipencet nanti)
@Composable
fun RoomCard(
    navController: NavController ?= null,
    route: String,
    context : Context,
    room : Room
) {
    val API_URL = BuildConfig.API_URL
    Box(
        modifier = Modifier
            .width(165.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                Log.d("RoomCard", "RoomCard: ${room.image}")
                if (room.is_available) {
                    route?.let { route ->
                        navController?.navigate(route)
                    }
                }
                else{
                    // pop a toast
                    Toast.makeText(context, "Sorry! Room is unavailable", Toast.LENGTH_SHORT).show()
                }
            }
    ) {

        if (!room.is_available){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        drawRect(
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                    }
                    .zIndex(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "UNAVAILABLE",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


        room.image?.let { imageUrl ->
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("${API_URL}${imageUrl}")
                    .crossfade(true)
                    .build(),
                placeholder = ColorPainter(Color.Transparent),
                contentDescription = stringResource(R.string.item_description, room.name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            AlmostWhite.copy(alpha = 0.0f),
                            BiruMuda_Lightest.copy(alpha = 0.35f),
                            BiruUMN.copy(alpha = 0.7f)
                        )
                    )
                )
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            // Class Code
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = AlmostWhite,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .background(
                            color = BiruUMN.copy(0.8f),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .padding(12.dp, 3.dp)
                ) {
                    Text(
                        text = room.location, // location aslinya blm ada
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight(700),
                            color = AlmostWhite,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
            Text(
                text = room.name,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(700),
                    color = AlmostWhite,
                )
            )
        }
    }
}

@Composable
fun ItemCard(
    navController: NavController ?= null,
    route: String,
    context : Context,
    item : Item
) {

    val API_URL = BuildConfig.API_URL

    Box(
        modifier = Modifier
            .width(165.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                if (item.reserved_qty > 0) {
                    route?.let { route ->
                        navController?.navigate(route)
                    }
                }
            }
    ) {

        if (item.reserved_qty <= 0){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        drawRect(
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                    }
                    .zIndex(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "UNAVAILABLE",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item.image?.let { imageUrl ->
            val processedUrl = imageUrl.replace("public/", "storage/")
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("${API_URL}/${processedUrl}")
                    .crossfade(true)
                    .build(),
                placeholder = ColorPainter(Color.Transparent),
                contentDescription = stringResource(R.string.item_description, item.name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            AlmostWhite.copy(alpha = 0.0f),
                            BiruMuda_Lightest.copy(alpha = 0.35f),
                            BiruUMN.copy(alpha = 0.7f)
                        )
                    )
                )
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            // Class Code
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = AlmostWhite,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .background(
                            color = BiruUMN.copy(0.8f),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .padding(12.dp, 3.dp)
                ) {
                    Text(
                        text = "${item.reserved_qty}/${item.quantity}",
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight(700),
                            color = AlmostWhite,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
            Text(
                text = item.name,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(700),
                    color = AlmostWhite,
                )
            )
        }
    }
}

@Composable
fun TextFieldTitle(title: String) {
    Text(
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        text = title,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp)
    )
}