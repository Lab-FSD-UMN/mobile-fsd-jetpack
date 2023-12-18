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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mobile_fsd_jetpack.BuildConfig.API_URL
import com.example.mobile_fsd_jetpack.R
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.room.RoomsApiService
import com.example.mobile_fsd_jetpack.api.request_body.room.RoomReservation
import com.example.mobile_fsd_jetpack.api.response_model.ApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomByIDApiResponse
import com.example.mobile_fsd_jetpack.auth.UserAuth
import com.example.mobile_fsd_jetpack.models.Room
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.LoadingScreen
import com.example.mobile_fsd_jetpack.ui.theme.PageHeading
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
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute -> onTimeSet(hourOfDay, minute) },
        currentHour,
        currentMinute,
        false
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
        context,
        { _, year, month, day ->
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
fun RoomReservationFormScreen(navController: NavController? = null, id: String?, imageUrl : String?) {
    val context = LocalContext.current

    var room by remember { mutableStateOf<Room?>(null) }
    var roomIsNotFound: Boolean = false
    var modalData by remember { mutableStateOf<ApiResponse?>(null) }

    val retrofit = BaseAPIBuilder().retrofit
    val getRoomsApiService = retrofit.create(RoomsApiService::class.java)


    // Declare a variable to track whether the submission is in progress
    var isSubmitting by remember { mutableStateOf(false) }
    var isSubmitted by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(id){
        if (id != null){
            val call = getRoomsApiService.getRoomById(id)

            call.enqueue(object : Callback<GetRoomByIDApiResponse> {
                override fun onResponse(call: Call<GetRoomByIDApiResponse>, response: Response<GetRoomByIDApiResponse>) {
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

                        Row {
                            Text(
                                text = it.name,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .padding(16.dp)
                                    .background(Color.Transparent)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
        //                        .height(150.dp)
                                .aspectRatio(1f)
                                .fillMaxHeight()
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(16.dp)
                        ) {

                            it.image?.let { imageUrl ->
                                Log.d("imageUrl", "${API_URL}/${imageUrl}")
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data("${API_URL}/${imageUrl}")
                                        .crossfade(true)
                                        .build(),
                                    placeholder = ColorPainter(Color.Transparent),
                                    contentDescription = stringResource(R.string.item_description, it.name),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .aspectRatio(1f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = MaterialTheme.shapes.medium
                                )
                        ){
                            var selectedStartDate by remember { mutableStateOf("Select Start Date") }
                            var selectedEndDate by remember { mutableStateOf("Select End Date") }
                            var startTime by remember { mutableStateOf("Select Start Time") }
                            var endTime by remember { mutableStateOf("Select End Time") }

                            Row {
                                BasicTextField(
                                    value = selectedStartDate,
                                    onValueChange = {
                                        selectedStartDate = it
                                    },
                                    modifier = Modifier
                                        // 50% width
                                        .weight(1f)
                                        .height(50.dp)
                                        .clickable {
                                            showDatePickerDialog(context) { selectedDateString ->
                                                selectedStartDate = selectedDateString
                                            }
                                        }
                                        .height(50.dp)
                                        .background(MaterialTheme.colorScheme.surface)
                                        .border(1.dp, Color.Black),
                                    enabled = false
                                )
                                BasicTextField(
                                    value = selectedEndDate,
                                    onValueChange = {
                                        selectedEndDate = it
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp)

                                        .clickable {
                                            showDatePickerDialog(context) { selectedDateString ->
                                                selectedEndDate = selectedDateString
                                            }
                                        }
                                        .height(50.dp)
                                        .background(MaterialTheme.colorScheme.surface)
                                        .border(1.dp, Color.Black),
                                    enabled = false
                                )

                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize(Alignment.Center)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp)
                                        .border(1.dp, Color.Black)
                                        .padding(8.dp)
                                        .background(MaterialTheme.colorScheme.surface)
                                        .clickable {
                                            showTimePickerDialog(context) { selectedHour, selectedMinute ->
                                                startTime = "Start Time : $selectedHour:$selectedMinute"
                                            }
                                        }
                                ) {
                                    BasicTextField(
                                        value = startTime,
                                        onValueChange = { startTime = it },
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Done
                                        ),
                                        modifier = Modifier.fillMaxSize(),
                                        enabled = false
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp)
                                        .border(1.dp, Color.Black)
                                        .padding(8.dp)
                                        .background(MaterialTheme.colorScheme.surface)
                                        .clickable {
                                            showTimePickerDialog(context) { selectedHour, selectedMinute ->
                                                endTime = "End Time : $selectedHour:$selectedMinute"
                                            }
                                        }
                                ) {
                                    BasicTextField(
                                        value = endTime,
                                        onValueChange = { endTime = it },
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Done
                                        ),
                                        modifier = Modifier.fillMaxSize(),
                                        enabled = false
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            var textInput by remember { mutableStateOf("") }

                            OutlinedTextField(
                                value = textInput,
                                onValueChange = { textInput = it },
                                label = { Text("Description (Purpose)") },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    // Validate input
                                    if (selectedEndDate == "Select End Date" ||
                                        selectedStartDate == "Select Start Date" ||
                                        startTime == "Select Start Time" ||
                                        endTime == "Select End Time" ||
                                        textInput.isBlank()) {
                                        // Show an error message or toast to inform the user to fill in all fields.
                                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                                        return@Button
                                    }


                                    isSubmitting = true
                                    // POST the reservation
                                    Log.d("date", selectedStartDate)
                                    Log.d("start_time", startTime)
                                    Log.d("end_time", endTime)
                                    Log.d("description", textInput)

                                    val userToken = UserAuth(context).getToken()
                                    val body = RoomReservation(
                                        room_id = id.toString(),
                                        reservation_date_start = selectedStartDate,
                                        reservation_date_end = selectedEndDate,
                                        reservation_time_end = "12:30",
                                        reservation_time_start = "11:30",
                                        note = textInput    // description
                                    )

                                    val call = getRoomsApiService.reserveRoom(
                                        "Bearer ${userToken}",
                                        body
                                    )


                                    call.enqueue(object : Callback<ApiResponse> {
                                        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                                            val responseBody = response.body()

                                            //log request body
                                            Log.d("request", call.request().body.toString())

                                            Log.d("request", call.request().body.toString())
                                            Log.d("message", responseBody?.message.toString())
                                            Log.d("error", response.body()?.error.toString())
                                            Log.d("status", responseBody?.status.toString())
                                            modalData = ApiResponse(
                                                status = responseBody?.status,
                                                message = responseBody?.message,
                                                data = responseBody?.data,
                                                error = responseBody?.error
                                            )

                                            // Show Alert Dialog
//                                    Toast.makeText(context, responseBody?.message, Toast.LENGTH_SHORT).show()
                                            isSubmitting = false    // hide the loading dialog
                                            isSubmitted = true
                                            // navigating back to the previous screen
//                                    navController?.popBackStack()
                                        }

                                        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
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
                                    .height(56.dp)
                                // make it semi transparent if the form is not valid
                            ) {
                                Text("Submit")
                            }


                            // Add an AlertDialog for loading
                            if (isSubmitting) {
                                AlertDialog(
                                    onDismissRequest = { isSubmitting = false },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentSize(Alignment.Center)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .wrapContentSize(Alignment.Center)
                                    )
                                }
                            }

                            if(isSubmitted)
                            {
                                AlertDialog(
                                    onDismissRequest = { isSubmitted = false },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentSize(Alignment.Center)
                                        .background(Color.White)
                                ) {
                                    Column {
                                        Text("Reservation Submitted")
                                        Button(onClick = {
                                            isSubmitted = false
                                            navController?.popBackStack()
                                        }) {
                                            Text("OK")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }
}

@Composable
fun RoomReservationFormScreenPreview() {
    RoomReservationFormScreen(id = "1", imageUrl = "${API_URL}/storage/room/room1.jpg")
}