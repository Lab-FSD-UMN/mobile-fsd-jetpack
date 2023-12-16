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
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ElevationOverlay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mobile_fsd_jetpack.navigation.MainNavRoutes
import com.example.mobile_fsd_jetpack.navigation.NavigationGraph
import com.example.mobile_fsd_jetpack.navigation.RouteProvider
import com.example.mobile_fsd_jetpack.navigation.getCategory
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.BiruMuda_Lightest
import com.example.mobile_fsd_jetpack.ui.theme.BiruUMN

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainNavController = rememberNavController()
            val context = LocalContext.current
            MobilefsdjetpackTheme {
                val bottomBarHeight = 56.dp
                val bottomBarOffsetHeightPx = remember { mutableFloatStateOf(0f) }
                var buttonsVisible = remember { mutableStateOf(true) }

                Scaffold(
                    bottomBar = {
                        BottomBar(
                            navController = mainNavController,
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
                        NavigationGraph(navController = mainNavController)
//                            ReservationNavigationGraph(navController = reservationNavController)
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

// Navigation graph is in another file /navigation
// Create bottom navigation with Material 3
@Composable
fun BottomBar(
    navController: NavHostController, state: MutableState<Boolean>, modifier: Modifier = Modifier
) {
    val screens = listOf (
        MainNavRoutes.Reservation, MainNavRoutes.Monitoring, MainNavRoutes.Profile
    )

    NavigationBar (
        modifier = modifier
            .shadow(
                elevation = 30.dp,
                spotColor = Color.Gray.copy(alpha = 0.8f)
            ),
        containerColor = BiruMuda_Lightest,
        contentColor = BiruUMN,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Log.d("ROUTE", currentRoute.toString())
//        Log.d("ROUTE", getCategory(currentRoute.toString()).toString())

        screens.forEach { screen ->
            val selected = currentRoute?.let { getCategory(it) } == screen.category

            NavigationBarItem(
                label = {
                    Text(
                        text = screen.title!!,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                },
                icon = {
                    Icon(
                        modifier = Modifier
                            .padding(0.dp, 2.dp)
                            .alpha(if (selected) 1f else 0.4f),
                        tint = BiruUMN,
                        imageVector =
                            when (screen.title)
                            {
                                "Reserve" -> ImageVector.vectorResource(R.drawable.reservation_icon)
                                "Monitoring" -> ImageVector.vectorResource(R.drawable.monitoring_icon)
                                "Profile" -> ImageVector.vectorResource(R.drawable.profile_icon)
                                else -> ImageVector.vectorResource(R.drawable.question_mark_icon)
                            },
                        contentDescription = "")
                },
                selected = selected,
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
                    unselectedTextColor = BiruUMN.copy(alpha = 0.4f),
                    selectedTextColor = BiruUMN,
                )
            )
        }
    }
}