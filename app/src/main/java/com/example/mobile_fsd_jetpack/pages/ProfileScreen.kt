package com.example.mobile_fsd_jetpack.pages

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.ui.theme.Background
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme
import com.example.mobile_fsd_jetpack.ui.theme.PrimaryTextButton

@Composable
fun ProfileScreen(navController: NavController ?= null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Profile Screen",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )

        PrimaryTextButton(
            text = "LOGOUT",
            onClick = {},
            navController = navController,
            route = "loginActivity",
            modifier = Modifier
                .padding(start = 24.dp, top = 12.dp, end = 24.dp, bottom = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    MobilefsdjetpackTheme {
        ProfileScreen()
    }
}