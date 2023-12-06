package com.example.mobile_fsd_jetpack.pages

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme
import com.example.mobile_fsd_jetpack.ui.theme.PrimaryTextButton

@Composable
fun ProfileScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.Center)
    ) {
        // Placeholder Image
        // You can use an Image composable here, replacing the placeholderDrawable with your actual image
        // Image(
        //     painter = painterResource(id = R.drawable.your_image_resource),
        //     contentDescription = "Profile Image",
        //     modifier = Modifier
        //         .size(120.dp)
        //         .clip(CircleShape)
        // )

        // Placeholder Name
        Text(
            text = "John Doe", // Replace with actual name
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            textAlign = TextAlign.Center,
        )

        // Placeholder NIM
        Text(
            text = "123456789", // Replace with actual NIM
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp),
            textAlign = TextAlign.Center,
        )

        // Logout Button
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