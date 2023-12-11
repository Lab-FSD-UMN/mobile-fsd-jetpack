package com.example.mobile_fsd_jetpack.pages.reservation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.mobile_fsd_jetpack.BuildConfig.API_URL
import com.example.mobile_fsd_jetpack.R
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.room.RoomsApiService
import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomByIDApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.room.GetRoomsApiResponse
import com.example.mobile_fsd_jetpack.models.Room
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
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


@Composable
fun RoomReservationFormScreen(navController: NavController? = null, id: String?, imageUrl : String?) {
    val context = LocalContext.current

    var room by remember { mutableStateOf<Room?>(null) }
    var roomIsNotFound: Boolean = false

    val retrofit = BaseAPIBuilder().retrofit
    val getRoomsApiService = retrofit.create(RoomsApiService::class.java)

    LaunchedEffect(id){
        if (id != null){
            val call = getRoomsApiService.getRoomById(id)

            call.enqueue(object : Callback<GetRoomByIDApiResponse> {
                override fun onResponse(call: Call<GetRoomByIDApiResponse>, response: Response<GetRoomByIDApiResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.data?.let { data ->
                            room = data
                        }

                    } else {
                        Log.d("e", response.message())
                    }
                }

                override fun onFailure(call: Call<GetRoomByIDApiResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .wrapContentSize(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
        ) {
            room?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp)
                ) {

                    Image(
                        painter = rememberImagePainter(
                            data = "${API_URL}${it.image}",
                            builder = {
                                crossfade(true)
                                placeholder(android.R.drawable.ic_menu_gallery)
                            }
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                    )
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .padding(16.dp)
                    )
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
                    var selectedDate by remember { mutableStateOf("Select Date") }
                    var startTime by remember { mutableStateOf("Select Start Time") }
                    var endTime by remember { mutableStateOf("Select End Time") }

                    BasicTextField(
                        value = selectedDate,
                        onValueChange = {
                            selectedDate = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDatePickerDialog(context) { selectedDateString ->
                                    selectedDate = selectedDateString
                                }
                            }
                            .height(50.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, Color.Black),
                        enabled = false
                    )

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
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text("Submit")
                    }

                }
            } ?: run {
                    Text(text="Loading")
                }
            }
        }
}



@Preview
@Composable
fun RoomReservationFormScreenPreview() {
    RoomReservationFormScreen(id = "1", imageUrl = "${API_URL}/storage/room/room1.jpg")
}