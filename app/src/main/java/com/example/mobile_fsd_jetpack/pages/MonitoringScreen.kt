package com.example.mobile_fsd_jetpack.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.Greeting
import com.example.mobile_fsd_jetpack.ui.theme.Background
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme

@Composable
fun MonitoringScreen(navController: NavController?= null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Monitoring Screen",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MonitorPreview() {
    MobilefsdjetpackTheme {
        MonitoringScreen()
    }
}