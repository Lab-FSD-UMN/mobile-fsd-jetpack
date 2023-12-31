package com.example.mobile_fsd_jetpack.navigation

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

) : RouteProvider {
    object Reservation : MainNavRoutes (
        route = "reservation_screen",
        title = "Reserve",
        category = "reservation",
    )

    object Monitoring : MainNavRoutes (
        route = "monitoring_screen",
        title = "Monitoring",
        category = "monitoring",
    )

    object Profile : MainNavRoutes (
        route = "profile_screen",
        title = "Profile",
        category = "profile",
    )
}

sealed class ReservationRoutes(
    override val route: String,
    override val title: String? = null,
    val id : String ?= null,
    override val category: String = "reservation",
) : RouteProvider {
    object RoomReservation : ReservationRoutes(
        route = "room_reservation",
        title = "Room Reservation",
    )

    object ItemReservation : ReservationRoutes(
        route = "item_reservation",
        title = "Item Reservation",
    )

    object RoomReservationForm : ReservationRoutes(
        route = "room_reservation_form",
        title = "Room Reservation Form",
        id = null
    )

    object ItemReservationForm : ReservationRoutes(
        route = "item_reservation_form",
        title = "Item Reservation Form",
        id = null
    )

    // nanti kalo uda berhasil reserve, munculin dialogue (?) terus langsung ke halaman monitoring
}

fun getCategory(route: String): String? {
    // CATEGORY
    val cat = listOf("reservation", "monitoring", "profile")

    return when (route) {
        // MAIN
        MainNavRoutes.Reservation.route -> cat[0]
        MainNavRoutes.Monitoring.route -> cat[1]
        MainNavRoutes.Profile.route -> cat[2]

        // RESERVATION
        ReservationRoutes.RoomReservation.route -> cat[0]
        ReservationRoutes.ItemReservation.route -> cat[0]
        ReservationRoutes.RoomReservationForm.route -> cat[0]
        ReservationRoutes.ItemReservationForm.route -> cat[0]
        "${ReservationRoutes.RoomReservationForm.route}/{id}" -> cat[0]
        "${ReservationRoutes.ItemReservationForm.route}/{id}" -> cat[0]

        // MONITORING
        // nanti tab navigation
        // paling kalo mau ada page detailnya

        // PROFILE
        // keanya gaada apa" yak

        else -> null
    }
}


