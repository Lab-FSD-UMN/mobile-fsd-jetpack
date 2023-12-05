package com.example.mobile_fsd_jetpack.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.R
import com.example.mobile_fsd_jetpack.navigation.ReservationRoutes
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.BiruMuda
import com.example.mobile_fsd_jetpack.ui.theme.BiruUMN
import com.example.mobile_fsd_jetpack.ui.theme.ButtonImage
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme
import com.example.mobile_fsd_jetpack.auth.UserAuth
import androidx.compose.ui.platform.LocalContext

@Composable
fun ReservationScreen(navController: NavController?= null) {

    val context = LocalContext.current;

    val name : String? = UserAuth(context).getNama();

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        Header(name);
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(8f / 1f),
            painter = painterResource(id = R.drawable.super_graphic),
            contentDescription = "Super Graphic"
        )
        Spacer(modifier = Modifier.height(20.dp))
        ButtonImage(
            text = "Room Reservation",
            image = R.drawable.room_reservation,
            onClick = {},
            route = ReservationRoutes.RoomReservation.route,
            navController = navController,
            imageRatio = 56f/27f,
            modifier = Modifier
                .padding(20.dp, 0.dp)
                .fillMaxWidth()
                .aspectRatio(56f / 27f)
        )

        Spacer(modifier = Modifier.height(20.dp))
        ButtonImage(
            text = "Item Reservation",
            image = R.drawable.room_reservation,
            onClick = {},
            route = ReservationRoutes.ItemReservation.route,
            navController = navController,
            imageRatio = 56f/27f,
            modifier = Modifier
                .padding(20.dp, 0.dp)
                .fillMaxWidth()
                .aspectRatio(56f / 27f)
        )
    }
}

// HEADER
@Composable
fun Header(name : String?) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BiruMuda,
                        BiruMuda.copy(alpha = 0.8f)
                    )
                )
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_labfsd),
                        contentDescription = "LAB FSD",
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(200f / 111f),
                        contentScale = ContentScale.FillBounds
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.logo_fsd),
                        contentDescription = "FSD",
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(314 / 75f),
                        contentScale = ContentScale.FillBounds
                    )
                }
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = BiruUMN,
                            shape = RoundedCornerShape(size = 100.dp)
                        )
                        .width(28.dp)
                        .height(28.dp)
                        .background(
                            color = Color(0x99D3F2FF),
                            shape = RoundedCornerShape(size = 100.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.question_mark_icon),
                        contentDescription = "LAB FSD",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Hi %s,\nWhat do you want to reserve?".format(name),
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight(600),
                    color = AlmostWhite,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReservationPreview() {
    MobilefsdjetpackTheme {
        ReservationScreen()
    }
}