package com.example.mobile_fsd_jetpack.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.R

@Composable
fun PrimaryTextButton(
    text: String,
    onClick: () -> Unit,
    navController: NavController ?= null,
    route: String ?= null,
    modifier: Modifier = Modifier
) {
    TextButton(
        text = text,
        textColor = Color.White,
        bgColor = Orange,
        onClick = {
            // Custom function
            onClick()
            // If the button will be used for navigation
            route?.let { route ->
                navController?.navigate(route)
            }
        },
//        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun TextButton(text: String, textColor: Color, bgColor: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = bgColor,
                contentColor = textColor
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun ButtonImage(
    text: String,
    image: Int,
    onClick: () -> Unit,
    navController: NavController ?= null,
    route: String ?= null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Room Reservation",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .scale(1f, 1f)
//                     .contentScale(ContentScale.Crop)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick()
                    route?.let { route ->
                        navController?.navigate(route)
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}
