package com.example.mobile_fsd_jetpack.pages.reservation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mobile_fsd_jetpack.BuildConfig.API_URL
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.room.RoomsApiService
import com.example.mobile_fsd_jetpack.api.request_body.room.RoomReservation
import com.example.mobile_fsd_jetpack.api.response_model.ApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomByIDApiResponse
import com.example.mobile_fsd_jetpack.auth.UserAuth
import com.example.mobile_fsd_jetpack.models.Room
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.BiruUMN
import com.example.mobile_fsd_jetpack.ui.theme.LoadingScreen
import com.example.mobile_fsd_jetpack.ui.theme.PageHeading
import com.example.mobile_fsd_jetpack.R
import com.example.mobile_fsd_jetpack.navigation.MainNavRoutes
import com.example.mobile_fsd_jetpack.ui.theme.BasicDialog
import com.example.mobile_fsd_jetpack.ui.theme.BiruMuda_Lightest
import com.example.mobile_fsd_jetpack.ui.theme.TextFieldTitle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


private fun formatDate(year: Int, month: Int, day: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(calendar.time)
}
private fun showTimePickerDialog(context: Context, onTimeSet: (hour: Int, minute: Int) -> Unit) {
    val calendar = Calendar.getInstance()
    // Get the current hour, minute, and second
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)
    val currentSecond = calendar.get(Calendar.SECOND)

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            onTimeSet(hourOfDay, minute)
        },
        currentHour,
        currentMinute,
        true // Set this to true to display the second picker
    )

    timePickerDialog.show()
}

@OptIn(ExperimentalMaterial3Api::class)
private fun showDatePickerDialog(context: Context, onDateSet: (selectedDate: String) -> Unit) {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context, { _, year, month, day ->
            val selectedDate = formatDate(year, month, day)
            onDateSet(selectedDate)
        },
        currentYear,
        currentMonth,
        currentDay
    ).show()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomReservationFormScreen(
    navController: NavController? = null, id: String?, imageUrl: String?
) {
    val context = LocalContext.current

    val userToken = UserAuth(context).getToken()

    var room by remember { mutableStateOf<Room?>(null) }
    var roomIsNotFound: Boolean = false
    var modalData by remember { mutableStateOf<ApiResponse?>(null) }

    var selectedStartDate by remember { mutableStateOf("Select Start Date") }
    var selectedEndDate by remember { mutableStateOf("Select End Date") }
    var startTime by remember { mutableStateOf("Select Start Time") }
    var endTime by remember { mutableStateOf("Select End Time") }


    val retrofit = BaseAPIBuilder().retrofit
    val getRoomsApiService = retrofit.create(RoomsApiService::class.java)


    // Declare a variable to track whether the submission is in progress
    var isSubmitting by remember { mutableStateOf(false) }
    var isSubmitted by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(id) {
        if (id != null) {
            val call = getRoomsApiService.getRoomById(id)
            call.enqueue(object : Callback<GetRoomByIDApiResponse> {
                override fun onResponse(
                    call: Call<GetRoomByIDApiResponse>, response: Response<GetRoomByIDApiResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("t", response.toString())
                        val responseBody = response.body()
                        responseBody?.data?.let { data ->
                            room = data
                        }
                    } else {
                        Log.d("e", response.message())
                    }

                    isLoading = false
                }

                override fun onFailure(call: Call<GetRoomByIDApiResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())

                    isLoading = false
                }
            })
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        PageHeading("Room Reservation Form", navController)
        when {
            isLoading -> LoadingScreen()
            else ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .wrapContentSize(Alignment.TopCenter)
                        .verticalScroll(rememberScrollState())

                ) {
                    room?.let {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp) // Adjust the height as needed
                                .background(
                                    color = Color.White, shape = RoundedCornerShape(8.dp)
                                )
                                .padding(5.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = Color.White, shape = RoundedCornerShape(8.dp)
                                    )
                                    .fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .padding(16.dp)
                                        .weight(1f)
                                ) {
                                    it.image?.let { imageUrl ->
                                        Log.d("imageUrl", "${API_URL}/${imageUrl}")
                                        AsyncImage(
                                            model = ImageRequest.Builder(context)
                                                .data("${API_URL}/${imageUrl}").crossfade(true)
                                                .build(),
                                            placeholder = ColorPainter(Color.Transparent),
                                            contentDescription = stringResource(
                                                R.string.item_description, it.name
                                            ),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    shape = RoundedCornerShape(8.dp),
                                                    color = Color.Transparent
                                                )
                                                .clip(shape = RoundedCornerShape(8.dp)) // Apply the clip to round the corners
                                        )
                                    }
                                }


                                Text(
                                    text = "${it.location} - ${it.name}",
                                    color = BiruUMN,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .align(Alignment.CenterVertically)
                                        .wrapContentSize(Alignment.Center)
                                        .weight(0.8f),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    ),
                                    textAlign = TextAlign.Center
                                )

                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(0.dp)
                                .background(
                                    color = Color.White, shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(16.dp)
                                    .background(
                                        color = Color.White, shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                var selectedStartDate by remember { mutableStateOf("dd / mm  / yyyy") }
                                var selectedEndDate by remember { mutableStateOf("dd / mm  / yyyy") }
                                var startTime by remember { mutableStateOf("Select Start Time") }
                                var endTime by remember { mutableStateOf("Select End Time") }
                                var textInput by remember { mutableStateOf("") }

                                // START DATE
                                TextFieldTitle("Date Start")
                                OutlinedTextField(
                                    value = selectedStartDate,
                                    onValueChange = {
                                        selectedStartDate = it
                                    },
                                    modifier = Modifier
                                        // 50% width
                                        .clickable {
                                            showDatePickerDialog(context) { selectedDateString ->
                                                selectedStartDate = selectedDateString
                                            }
                                        }
                                        .border(
                                            1.dp, Color.Gray.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .fillMaxWidth(),
                                    enabled = false,
                                    textStyle = TextStyle(color = Color.Gray.copy(alpha = 0.8f)), // Apply the desired text color here
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                // END DATE
                                TextFieldTitle("Date End")
                                OutlinedTextField(
                                    value = selectedEndDate,
                                    onValueChange = {
                                        selectedEndDate = it
                                    },
                                    modifier = Modifier
                                        // 50% width
                                        .clickable {
                                            showDatePickerDialog(context) { selectedDateString ->
                                                selectedEndDate = selectedDateString
                                            }
                                        }
                                        .border(
                                            1.dp, Color.Gray.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .fillMaxWidth(),
                                    enabled = false,
                                    textStyle = TextStyle(color = Color.Gray.copy(alpha = 0.8f)), // Apply the desired text color here

                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                // TIME START
                                TextFieldTitle("Time Start")
                                OutlinedTextField(
                                    value = startTime,
                                    onValueChange = { startTime = it },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                                    ),
                                    modifier = Modifier
                                        .border(
                                            1.dp, Color.Gray.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            showTimePickerDialog(context) { selectedHour, selectedMinute ->
                                                val formattedTime = String.format(
                                                    "%02d:%02d:00",
                                                    selectedHour,
                                                    selectedMinute
                                                )
                                                startTime = formattedTime
                                            }
                                        }
                                        .fillMaxWidth(),
                                    textStyle = TextStyle(color = Color.Gray.copy(alpha = 0.8f)), // Apply the desired text color here
                                    enabled = false
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                // TIME END
                                TextFieldTitle("Time End")
                                OutlinedTextField(
                                    value = endTime,
                                    onValueChange = { endTime = it },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                                    ),
                                    modifier = Modifier
                                        .border(
                                            1.dp, Color.Gray.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            showTimePickerDialog(context) { selectedHour, selectedMinute ->
                                                val formattedTime = String.format(
                                                    "%02d:%02d:00",
                                                    selectedHour,
                                                    selectedMinute
                                                )
                                                endTime = formattedTime
                                            }
                                        }
                                        .fillMaxWidth(),
                                    textStyle = TextStyle(color = Color.Gray.copy(alpha = 0.8f)), // Apply the desired text color here
                                    enabled = false
                                )

                                Spacer(modifier = Modifier.height(32.dp))

                                // Description
                                TextFieldTitle("Description (Purpose)")
                                OutlinedTextField(
                                    value = textInput,
                                    onValueChange = { textInput = it },
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                                    modifier = Modifier.fillMaxWidth(),
                                    // change text color to black
                                    textStyle = TextStyle(color = Color.Gray.copy(alpha = 0.8f)), // Apply the desired text color here
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        // Validate input
                                        if (selectedEndDate == "dd / mm  / yyyy" || selectedStartDate == "dd / mm  / yyyy" || startTime == "Select Start Time" || endTime == "Select End Time" || textInput.isBlank()) {
                                            // Show an error message or toast to inform the user to fill in all fields.
                                            Toast.makeText(
                                                context,
                                                "Please fill in all fields",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            return@Button
                                        }

                                        isSubmitting = true
                                        // POST the reservation
                                        Log.d("start date", selectedStartDate)
                                        Log.d("end date", selectedEndDate)
                                        Log.d("start_time", startTime)
                                        Log.d("end_time", endTime)
                                        Log.d("description", textInput)

                                        val userToken = UserAuth(context).getToken()
                                        val body = RoomReservation(
                                            room_id = id.toString(),
                                            reservation_date_start = selectedStartDate,
                                            reservation_date_end = selectedEndDate,
                                            reservation_time_end = startTime,
                                            reservation_time_start = endTime,
                                            note = textInput    // description
                                        )

                                        val call = getRoomsApiService.reserveRoom(
                                            "Bearer $userToken", body
                                        )

                                        call.enqueue(object : Callback<ApiResponse> {
                                            override fun onResponse(
                                                call: Call<ApiResponse>,
                                                response: Response<ApiResponse>
                                            ) {
                                                Log.d("message", response.code().toString())
                                                val responseBody = response.body()
                                                //log request body
                                                modalData = ApiResponse(
                                                    status = responseBody?.status,
                                                    message = responseBody?.message,
                                                    data = responseBody?.data,
                                                    error = responseBody?.error
                                                )
                                                isSubmitting = false    // hide the loading dialog
                                                isSubmitted = true
                                            }


                                            override fun onFailure(
                                                call: Call<ApiResponse>,
                                                t: Throwable
                                            ) {
                                                Log.d("onFailure", t.message.toString())
                                                modalData = ApiResponse(
                                                    status = 500,
                                                    message = "Failed to process your reservation."
                                                )
                                            }
                                        })

                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        BiruUMN
                                    ),
                                ) {
                                    Text(
                                        text = "Submit",
                                        color = Color.White,
                                    )
                                }

                                // Add an AlertDialog for loading
                                if (isSubmitting) {
                                    AlertDialog(
                                        onDismissRequest = { isSubmitting = false },
                                        properties = DialogProperties(
                                            dismissOnClickOutside = false,
                                            dismissOnBackPress = false,
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentSize(Alignment.Center)
                                    ) {
                                        LoadingScreen(color = BiruMuda_Lightest)
                                    }
                                }

                                if (isSubmitted) {
                                    BasicDialog(
                                        onDismiss = {
                                            navController?.navigate(MainNavRoutes.Monitoring.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                            isSubmitted = false
                                        },
                                        onDismissClickOutside = false ,
                                        title = if (modalData?.status == 500) "Fail" else "Success",
                                        buttonText = "OK",
                                        content = {
                                            Text(text = modalData?.message.toString())
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }
}


@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier.padding(8.dp)
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        modifier = modifier
    )
}


//@OptIn(ExperimentalCoilApi::class)
//@Composable
//@Preview
//fun RoomReservationFormScreenPreview() {
//    RoomReservationFormScreen(
//        id = "1",
//        imageUrl = "${API_URL}/storage/room/room1.jpg")
//}