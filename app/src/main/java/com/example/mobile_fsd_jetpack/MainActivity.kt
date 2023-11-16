package com.example.mobile_fsd_jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_fsd_jetpack.auth.UserAuth
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme
import com.example.mobile_fsd_jetpack.LoginActivity
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mobile_fsd_jetpack.pages.MonitoringScreen
import com.example.mobile_fsd_jetpack.pages.ProfileScreen
import com.example.mobile_fsd_jetpack.pages.ReservationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            MobilefsdjetpackTheme {
                val bottomBarHeight = 56.dp
                val bottomBarOffsetHeightPx = remember { mutableFloatStateOf(0f) }
                var buttonsVisible = remember { mutableStateOf(true) }

                Scaffold(
                    bottomBar = {
                        BottomBar(
                            navController = navController,
                            state = buttonsVisible,
                            modifier = Modifier
                        )
                    }) { paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues)
                    ) {
//                        if (!UserAuth(context).authenticated()){
//                            val intent = Intent(context, LoginActivity::class.java)
//                            context.startActivity(intent)
//                        }
//                        else {
                            NavigationGraph(navController = navController)
//                        }
                    }
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobilefsdjetpackTheme {
        Greeting("MAIN ACTIVITY")
    }
}

// Reference : https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
sealed class Routes (
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object Reservation : Routes (
        route = "reservation_screen",
        title = "Reserve",
        icon = Icons.Outlined.Home
    )

    object Monitoring : Routes (
        route = "monitoring_screen",
        title = "Monitoring",
        icon = Icons.Outlined.Favorite
    )

    object Profile : Routes (
        route = "profile_screen",
        title = "Profile",
        icon = Icons.Outlined.Person
    )
}

// Screens to displayed are defined in another file /pages
// Define Navigation Graph
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.Reservation.route) {
        composable("loginActivity") {
            val context = LocalContext.current
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
        composable(Routes.Reservation.route) {
            ReservationScreen()
        }
        composable(Routes.Monitoring.route) {
            MonitoringScreen()
        }
        composable(Routes.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}

fun finish() {
    TODO("Not yet implemented")
}

// Create bottom navigation with Material 3
@Composable
fun BottomBar(
    navController: NavHostController, state: MutableState<Boolean>, modifier: Modifier = Modifier
) {
    val screens = listOf (
        Routes.Reservation, Routes.Monitoring, Routes.Profile
    )

    NavigationBar (
        modifier = modifier,
        containerColor = Color.LightGray
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(text = screen.title!!)
                },
                icon = {
                    Icon(imageVector = screen.icon!!, contentDescription = "")
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Gray, selectedTextColor = Color.White
                )
            )
        }
    }
}