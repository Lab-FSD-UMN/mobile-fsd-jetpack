package com.example.mobile_fsd_jetpack.ui.theme

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

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
