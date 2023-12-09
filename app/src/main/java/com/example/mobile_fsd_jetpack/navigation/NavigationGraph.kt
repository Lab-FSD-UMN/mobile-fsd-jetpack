package com.example.mobile_fsd_jetpack.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile_fsd_jetpack.LoginActivity
import com.example.mobile_fsd_jetpack.pages.MonitoringScreen
import com.example.mobile_fsd_jetpack.pages.ProfileScreen
import com.example.mobile_fsd_jetpack.pages.ReservationScreen
import com.example.mobile_fsd_jetpack.pages.reservation.ItemReservationFormScreen
import com.example.mobile_fsd_jetpack.pages.reservation.ItemReservationScreen
import com.example.mobile_fsd_jetpack.pages.reservation.RoomReservationFormScreen
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
        composable("${ReservationRoutes.RoomReservationForm.route}/{id}") {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val id = navBackStackEntry?.arguments?.getString("id")
            RoomReservationFormScreen(navController = navController, id = id)
        }
        composable("${ReservationRoutes.ItemReservationForm.route}/{id}") {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val id = navBackStackEntry?.arguments?.getString("id")
            ItemReservationFormScreen(navController = navController, id = id)
        }

        // MONITORING
    }
}