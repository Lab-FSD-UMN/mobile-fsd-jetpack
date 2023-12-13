package com.example.mobile_fsd_jetpack.pages.monitoring

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.mobile_fsd_jetpack.ui.theme.Green
import com.example.mobile_fsd_jetpack.ui.theme.Red
import com.example.mobile_fsd_jetpack.ui.theme.Yellow
import java.text.SimpleDateFormat
import java.util.Locale

// MODEL
class GeneralMonitoringData (
    val status: StatusInfo,
    val reservationStart: DateTime,
    val reservationEnd: DateTime,
    val note : String ?= "",
)

class StatusInfo(val text: String, val color: Color)
class DateTime (val date: String, val time: String)

// FUNCTION
fun formatStatus(status: Int) : StatusInfo {
    return when (status) {
        1 -> StatusInfo("Approved", Green)
        0 -> StatusInfo("Pending", Yellow)
        2 -> StatusInfo("Rejected", Red)
        else -> StatusInfo("Unknown", Color.Gray)
    }
}

fun formatDateTime(input: String): DateTime {
    Log.d("CONSOLE", input)
    val inputFormat = SimpleDateFormat("EEEE, yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
    val outputTimeFormat = SimpleDateFormat("HH.mm", Locale.getDefault())

    val date = inputFormat.parse(input)

    val formattedDate = outputDateFormat.format(date!!)
    val formattedTime = outputTimeFormat.format(date)

    return DateTime(formattedDate, formattedTime)
}
