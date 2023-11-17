package com.example.mobile_fsd_jetpack.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile_fsd_jetpack.LoginActivity
import com.example.mobile_fsd_jetpack.pages.MonitoringScreen
import com.example.mobile_fsd_jetpack.pages.ProfileScreen
import com.example.mobile_fsd_jetpack.pages.ReservationScreen
import com.example.mobile_fsd_jetpack.pages.reservation.ItemReservationScreen
import com.example.mobile_fsd_jetpack.pages.reservation.RoomReservationScreen

// Screens to displayed are defined in another file /pages
// Define Navigation Graph
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = MainNavRoutes.Reservation.route) {
        composable("loginActivity") {
            val context = LocalContext.current
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
        composable(MainNavRoutes.Reservation.route) {
            ReservationScreen(navController = navController)
        }
        composable(MainNavRoutes.Monitoring.route) {
            MonitoringScreen(navController = navController)
        }
        composable(MainNavRoutes.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // RESERVATION
        composable(ReservationRoutes.RoomReservation.route) {
            RoomReservationScreen(navController = navController)
        }
        composable(ReservationRoutes.ItemReservation.route) {
            ItemReservationScreen(navController = navController)
        }
    }
}