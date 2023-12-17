package com.example.mobile_fsd_jetpack.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.example.mobile_fsd_jetpack.ui.theme.BasicDialog

@Composable
fun ReservationScreen(navController: NavController?= null) {
    val context = LocalContext.current
    val name : String? = UserAuth(context).getNama()

    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if(showDialog) {
            BasicDialog(
                onDismiss = { showDialog = false },
                onDismissClickOutside = true,
                title = "Credit",
                buttonText = "OK",
                content = {
                    Column{
                        Text(text = "Aurelius Ivan Wijaya (00000054769)")
                        Text(text = "Farrel Dinarta (00000055702)")
                        Text(text = "Maecyntha Irelynn Tantra (00000055038)")
                        Text(text = "Prudence Tendy (00000060765)")
                    }
                }
            )
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(color = AlmostWhite)
                .wrapContentSize(Alignment.TopCenter)
        ) {
            Header(name, onDialogButtonClick = { showDialog = true })
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(8f / 1f),
                painter = painterResource(id = R.drawable.super_graphic),
                contentDescription = "Super Graphic"
            )
            Column (
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                ButtonImage(
                    text = "Room Reservation",
                    image = R.drawable.room_reservation,
                    onClick = {},
                    route = ReservationRoutes.RoomReservation.route,
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
//                        .height(140.dp)
                )
                ButtonImage(
                    text = "Item Reservation",
                    image = R.drawable.room_reservation,
                    onClick = {},
                    route = ReservationRoutes.ItemReservation.route,
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
//                        .height(140.dp)
                )
            }
        }
    }
}

// HEADER
@Composable
fun Header(name : String?, onDialogButtonClick: () -> Unit) {
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
                        .clickable { onDialogButtonClick() }
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