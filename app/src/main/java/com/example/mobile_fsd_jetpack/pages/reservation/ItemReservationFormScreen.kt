package com.example.mobile_fsd_jetpack.pages.reservation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.rememberImagePainter
import com.example.mobile_fsd_jetpack.BuildConfig.API_URL
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.item.ItemsApiService
import com.example.mobile_fsd_jetpack.api.request_body.item.ItemReservation
import com.example.mobile_fsd_jetpack.api.response_model.ApiResponse
import com.example.mobile_fsd_jetpack.api.response_model.item.GetItemByIDApiResponse
import com.example.mobile_fsd_jetpack.auth.UserAuth
import com.example.mobile_fsd_jetpack.models.Item
import com.example.mobile_fsd_jetpack.navigation.MainNavRoutes
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.BasicDialog
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
fun ItemReservationFormScreen(navController: NavController? = null, id: String?, imageUrl : String?) {
    val context = LocalContext.current

    var item by remember { mutableStateOf<Item?>(null) }
    var itemIsNotFound by remember { mutableStateOf(false)}
//    var showDialog by remember { mutableStateOf(false) }
    var modalData by remember { mutableStateOf<ApiResponse?>(null) }

    val retrofit = BaseAPIBuilder().retrofit
    val getItemsApiService = retrofit.create(ItemsApiService::class.java)

    LaunchedEffect(id) {
        if (id != null){
            val call = getItemsApiService.getItemById(id)
            call.enqueue(object : Callback<GetItemByIDApiResponse> {
                override fun onResponse(call: Call<GetItemByIDApiResponse>, response: Response<GetItemByIDApiResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.data?.let { data ->
                            item = data
                        }

                    } else {
                        Log.d("e", response.message())
                    }
                }

                override fun onFailure(call: Call<GetItemByIDApiResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
        }
    }
//

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        PageHeading("Item Reservation Form", navController)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .wrapContentSize(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
        ) {
            item?.let {
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
                    var quantity by remember { mutableStateOf(1) }
                    var description by remember { mutableStateOf("") }


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
                                        startTime = "$selectedHour:$selectedMinute"
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
                                        endTime = "$selectedHour:$selectedMinute"
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

                    OutlinedTextField(
                        value = quantity.toString(),
                        onValueChange = {
                            quantity = it.toIntOrNull() ?: 0
                        },
                        label = { Text("Quantity") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description (Purpose)") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        modifier = Modifier.fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // POST the reservation

                            Log.d("date", selectedDate)
                            Log.d("start_time", startTime)
                            Log.d("end_time", endTime)
                            Log.d("qty", quantity.toString())
                            Log.d("desc", description)

                            val userToken = UserAuth(context).getToken()

                            val body = ItemReservation(
                                item_id = it.id,
                                quantity = quantity,
                                reservation_date_start = selectedDate,
                                reservation_date_end = selectedDate,
                                reservation_time_start = startTime,
                                reservation_time_end = endTime,
                                note = description
                            )

                            val call = getItemsApiService.reserveItem(
                                "Bearer ${userToken}",
                                body
                            )

                            call.enqueue(object : Callback<ApiResponse> {
                                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                                    val responseBody = response.body()
                                    Log.d("bid", responseBody.toString())
                                    modalData = ApiResponse(
                                        status = responseBody?.status,
                                        message = responseBody?.message
                                        // masih gagal klo buat response 400, ntah knp kalo 201 slalu bs kebaca statusny
                                        // slain itu null trs
                                    )
                                }

                                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
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
                    ) {
                        Text("Submit")
                    }

                }
            } ?: run {
                Text(text="Loading")
            }
        }

        if (modalData != null){
            val title = if (modalData?.status == 201) "Success" else "Fail"

            BasicDialog(
                onDismiss = {
                    modalData = null
                    navController?.navigate(MainNavRoutes.Monitoring.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    // biar kalo mau langsung ke tab item, tp navigasi ke yg lain jadi error :) jadi nantian aja
                    // navController?.navigate("${MainNavRoutes.Monitoring.route}/1")
                },
                onDismissClickOutside = false ,
                title = title,
                buttonText = "OK",
                content = {
                    Text(text = modalData?.message?.ifEmpty { "-" } ?: "-")
                }
            )
        }
    }
}

@Composable
fun ShowDialog(status : Int?, message: String?, onDismiss: () -> Unit) {
    val title = if (status == 201) "Success" else "Fail"
    BasicDialog(
        onDismiss = { onDismiss() },
        onDismissClickOutside = false,
        title = title,
        buttonText = "OK",
        content = {
            Text(text = message ?: "-")
        }
    )
}

@Preview
@Composable
fun ItemReservationFormScreenPreview() {
    ItemReservationFormScreen(id = "1", imageUrl = "${API_URL}/storage/item/cam1.jpg")
}