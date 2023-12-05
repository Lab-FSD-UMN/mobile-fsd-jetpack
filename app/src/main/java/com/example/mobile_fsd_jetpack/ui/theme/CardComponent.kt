package com.example.mobile_fsd_jetpack.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.R

// nanti tambahin parameter gambar, nama, kode kelas, apa lagi??
@Composable
fun RoomCard(
    navController: NavController ?= null,
    route: String,
) {
    Box(
        modifier = Modifier
            .width(165.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                route?.let { route ->
                    navController?.navigate(route)
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.lab1),
            contentDescription = "A Room",
            contentScale = ContentScale.Crop,
        )
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
                        text = "B301",
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
                text = "Illustration and Publishing Laboratory",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(700),
                    color = AlmostWhite,
                )
            )
        }
    }
}