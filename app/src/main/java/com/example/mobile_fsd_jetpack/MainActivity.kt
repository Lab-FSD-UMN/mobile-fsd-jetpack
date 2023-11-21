package com.example.mobile_fsd_jetpack

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
//import com.google.accompanist.navigation.material3.BottomNavigation
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_fsd_jetpack.auth.UserAuth
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme
import com.example.mobile_fsd_jetpack.LoginActivity
import android.content.Intent
import androidx.navigation.NavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current;

//            UserAuth(context).deleteProfile()
//            UserAuth(context).revokeToken()
            MobilefsdjetpackTheme {
                NavHost(navController = navController, startDestination = "mainActivity") {
                    composable("mainActivity") {

                        if (!UserAuth(context).authenticated()){
                            navController.navigate("loginActivity")
                        }
                        Home(navController, context)
                    }
                    composable("loginActivity") {
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

}

@Composable
fun Home(navController : NavController, context : Context) {


    val nama = UserAuth(context).getNama()

    Text(text="Hello $nama")
}

