package com.example.mobile_fsd_jetpack.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface RouteProvider {
    val route: String
    val title: String?
    val category: String
}

sealed class MainNavRoutes(
    override val route: String,
    override val title: String? = null,
    override val category: String,
    val icon: ImageVector? = null,
) : RouteProvider {
    object Reservation : MainNavRoutes (
        route = "reservation_screen",
        title = "Reserve",
        category = "reservation",
        icon = Icons.Outlined.Home
    )

    object Monitoring : MainNavRoutes (
        route = "monitoring_screen",
        title = "Monitoring",
        category = "monitoring",
        icon = Icons.Outlined.Favorite
    )

    object Profile : MainNavRoutes (
        route = "profile_screen",
        title = "Profile",
        category = "profile",
        icon = Icons.Outlined.Person
    )

    companion object {
        private fun fromRoute(route: String): String? {
            return when (route) {
                Reservation.route -> "Reservation"
                Monitoring.route -> "Monitoring"
                Profile.route -> "Profile"
                else -> null
            }
        }
    }
}

sealed class ReservationRoutes(
    override val route: String,
    override val title: String? = null,
    override val category: String,
) : RouteProvider {
    object RoomReservation : ReservationRoutes(
        route = "room_reservation",
        title = "Room Reservation",
        category = "reservation",
    )

    object ItemReservation : ReservationRoutes(
        route = "item_reservation",
        title = "Item Reservation",
        category = "reservation",
    )

    companion object {
        fun fromRoute(route: String): String? {
            return when (route) {
                RoomReservation.route -> "RoomReservation"
                ItemReservation.route -> "ItemReservation"
                else -> null
            }
        }
    }
}

fun getCategory(route: String): String? {
    return when (route) {
        // MAIN
        MainNavRoutes.Reservation.route -> MainNavRoutes.Reservation.category
        MainNavRoutes.Monitoring.route -> MainNavRoutes.Monitoring.category
        MainNavRoutes.Profile.route -> MainNavRoutes.Profile.category

        // RESERVATION
        ReservationRoutes.RoomReservation.route -> ReservationRoutes.RoomReservation.category
        ReservationRoutes.RoomReservation.route -> ReservationRoutes.RoomReservation.category
        else -> null
    }
}


