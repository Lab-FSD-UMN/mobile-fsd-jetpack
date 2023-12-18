package com.example.mobile_fsd_jetpack.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.R
import com.example.mobile_fsd_jetpack.navigation.MainNavRoutes

enum class NotGoingWellTypes {
    NoReservation,
    EmptyList,
    Error
}
@Composable
fun NotGoingWellDisplay(
    navController: NavController?= null,
    type: NotGoingWellTypes
) {
    val title = when(type) {
        NotGoingWellTypes.NoReservation -> "No Reservations yet"
        NotGoingWellTypes.EmptyList -> "Empty List"
        NotGoingWellTypes.Error -> "An Error Occurred"
        else -> "Unknown Situation"
    }

    val subTitle = when(type) {
        NotGoingWellTypes.NoReservation -> "Make your first reservation"
        NotGoingWellTypes.EmptyList -> "Try another keyword"
        NotGoingWellTypes.Error -> "Try closing the app"
        else -> "We're sorry"
    }

    val image = when(type) {
        NotGoingWellTypes.NoReservation -> R.drawable.empty_box
        NotGoingWellTypes.EmptyList -> R.drawable.empty_box
        NotGoingWellTypes.Error -> R.drawable.error
        else -> R.drawable.unknown
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ){
        Text(
            text = title,
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight(700),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = subTitle,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = Color(0x99000000),
                textAlign = TextAlign.Center,
            )
        )
        Image(
            painter = painterResource(id = image),
            contentDescription = "what a situation",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(0.dp, 30.dp)
                .height(180.dp)
        )

        if (type == NotGoingWellTypes.NoReservation) {
            Button(
                onClick = { navController?.navigate(MainNavRoutes.Reservation.route) },
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = Color(0xFF006BBD),
                        contentColor = AlmostWhite
                    )
            ) {
                Row(
                    modifier = Modifier.padding(24.dp, 5.dp)
                ) {
                    Text(
                        text = "Reserve Now",
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Icon(Icons.Default.ArrowForward, contentDescription = "go")
                }
            }
        }
    }
}