package com.example.mobile_fsd_jetpack

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile_fsd_jetpack.api.utils.LoginCallback
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme
import com.example.mobile_fsd_jetpack.auth.UserAuth
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.BiruMuda
import com.example.mobile_fsd_jetpack.ui.theme.BiruMuda_Lighter
import com.example.mobile_fsd_jetpack.ui.theme.BiruMuda_Lightest
import com.example.mobile_fsd_jetpack.ui.theme.BiruUMN
import com.example.mobile_fsd_jetpack.ui.theme.LoadingScreen
import com.example.mobile_fsd_jetpack.ui.theme.Orange
import com.example.mobile_fsd_jetpack.R

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            MobilefsdjetpackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavHost(navController = navController, startDestination = "loginActivity") {
                        composable("loginActivity") {
                            LoginForm(navController, context)
//                            LoginPrev()
                        }
                        composable("mainActivity") {

                            if (!UserAuth(context).authenticated()){
                                navController.navigate("loginActivity")
                            }

                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                        }

                    }

                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(navController : NavController, context : Context) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // UriHandler parse and opens URI inside AnnotatedString Item in Browse
    val uriHandler = LocalUriHandler.current

    val text = buildAnnotatedString {
        append("Don't have an account?")

        pushStringAnnotation(tag = "register", annotation = "https://fsd.renara.biz.id/register")
        withStyle(style = SpanStyle(
            textDecoration = TextDecoration.Underline, 
            color = Color(0xFF827D7D)
        )) {
            append(" Register now")
        }
        pop()
    }

    val focusManager = LocalFocusManager.current
//    val keyboardController = LocalSoftwareKeyboardController.current

    var isLoading by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AlmostWhite)
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(R.drawable.logo_fsd),
                contentDescription = "logo_fsd",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentScale = ContentScale.Crop,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(35.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = BiruMuda_Lightest,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(25.dp, 40.dp),

                ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Email Field
                    Text(
                        text = "Email",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            color = Color.Black,
                        )
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email
                        ),
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(300),
                            color = BiruUMN,
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = BiruMuda_Lighter,
                            unfocusedBorderColor = BiruMuda_Lighter
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .background(
                                color = AlmostWhite,
                                shape = RoundedCornerShape(size = 12.dp),
                            )
                    )
                }


                // Password Field
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Password",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            color = Color.Black,
                        )
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        trailingIcon = {
                            IconButton(
                                onClick = { isPasswordVisible = !isPasswordVisible },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(
                                        id =
                                        if (isPasswordVisible) R.drawable.pass_hide
                                        else R.drawable.pass_show
                                    ),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color.Gray),
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(300),
                            color = BiruUMN,
                        ),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = BiruMuda_Lighter,
                            unfocusedBorderColor = BiruMuda_Lighter
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .background(
                                color = AlmostWhite,
                                shape = RoundedCornerShape(size = 12.dp),
                            )
                        //                .softwareKeyboard(keyboardController)
                    )
                }

                // Submit Button
                Button(
                    enabled = !isLoading,
                    onClick = {
                        isLoading = true
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            println("Email: $email, Password: $password")
                            val login =
                                UserAuth(context).login(email, password, object : LoginCallback {
                                    override fun onLoginSuccess() {
                                        //                            navController.navigate("mainActivity")
                                        //                            Log.d("Success", "Success")
                                        //                            Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                                        val nim = "123456789"
                                        if (nim != null) {
                                            isLoading = false
                                            navController.navigate("mainActivity")
                                            Log.d("Success", "Success")
                                            Toast.makeText(
                                                context,
                                                "Login Success",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            isLoading = false
                                            // Handle the case where nim is null
                                            Log.e("Error", "nim is null")
                                            Toast.makeText(
                                                context,
                                                "Login Failed: nim is null",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                    override fun onLoginFailure() {
                                        //                            navController.navigate("mainActivity")
                                        isLoading = false
                                        Log.d("Fail", "Fail")
                                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                })


                        }

                        else {
                            isLoading = false
                            Toast.makeText(
                                context,
                                "Fill in both email and password fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = Orange,
                            contentColor = AlmostWhite
                        )
                ) {
                    Text(
                        text = "LOGIN",
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 24.dp),
                    )
                }

                ClickableText(
                    text = text,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF827D7D),
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline,
                    ),
                    onClick = { offset ->
                        text.getStringAnnotations("register", offset, offset)
                            .firstOrNull()?.let {
                                Log.d("CONSOLE", it.item)
                                uriHandler.openUri(it.item)
                            }
                    }
                )
            }

            Image(
                painter = painterResource(R.drawable.logo_labfsd),
                contentDescription = "logo_lab",
                modifier = Modifier
                    .width(50.dp),
                contentScale = ContentScale.FillWidth
            )
        }

        if(isLoading) {
            AlertDialog(
                onDismissRequest = { isLoading = false },
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
    }
}
