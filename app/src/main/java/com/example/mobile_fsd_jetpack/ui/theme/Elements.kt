package com.example.mobile_fsd_jetpack.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController

@Composable
fun PrimaryTextButton(
    text: String,
    onClick: () -> Unit,
    navController: NavController ?= null,
    route: String ?= null,
    modifier: Modifier = Modifier
) {
    TextButton(
        text = text,
        textColor = Color.White,
        bgColor = Orange,
        onClick = {
            // Custom function
            onClick()
            // If the button will be used for navigation
            route?.let { route ->
                navController?.navigate(route)
            }
        },
//        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun TextButton(text: String, textColor: Color, bgColor: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = bgColor,
                contentColor = textColor
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun ButtonImage(
    text: String,
    image: Int,
    onClick: () -> Unit,
    navController: NavController ?= null,
    route: String ?= null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Room Reservation",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick()
                    route?.let { route ->
                        navController?.navigate(route)
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontSize = 22.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun PageHeading(title: String, navController: NavController ?= null) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BiruMuda,
                        BiruMuda.copy(alpha = 0.8f)
                    )
                )
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(0.dp, 4.dp)
        ) {
            // Back button
            IconButton(
                onClick = {
                    navController?.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = AlmostWhite
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 20.sp,
                    color = AlmostWhite,
                )
            )
        }
    }
}

@Composable
fun LoadingScreen(color: Color = BiruUMN) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = color
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(placeholder: String, searchText: String, onSearchTextChanged: (String) -> Unit, onClick: () -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = { onSearchTextChanged(it) },
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(300),
                    color = BiruUMN,
                )
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {onClick()}),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight(300),
            color = BiruUMN,
        ),
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = {onClick()}
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = BiruUMN,
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .background(
                color = BiruMuda_Lightest,
                shape = RoundedCornerShape(size = 12.dp),
            )
    )
}


// DIALOG
@Composable
fun BasicDialog(
    onDismiss: () -> Unit,
    onDismissClickOutside: Boolean,
    title: String,
    buttonText: String,
    content: @Composable () -> Unit = { DefaultDialogContent() }
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = onDismissClickOutside,
            dismissOnBackPress = false,
        ),
        title = { Text(title) },
        text = { content() },
        confirmButton = {
            TextButton(
                text = buttonText,
                textColor = BiruMuda,
                bgColor = BiruMuda_Lightest,
                onClick = onDismiss
            )
        },
        containerColor = AlmostWhite,
        titleContentColor = BiruUMN,
        textContentColor = BiruMuda,
    )
}

@Composable
fun DefaultDialogContent() {
    Text(text = "This is a dialog")
}