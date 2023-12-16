package com.example.mobile_fsd_jetpack.auth

import android.content.Context
import android.util.Log
import com.example.mobile_fsd_jetpack.LoginActivity
import com.example.mobile_fsd_jetpack.MainActivity
import com.example.mobile_fsd_jetpack.api.BaseAPIBuilder
import com.example.mobile_fsd_jetpack.api.endpoints.LoginApiService
import com.example.mobile_fsd_jetpack.api.response_model.LoginApiResponse
import com.example.mobile_fsd_jetpack.api.utils.LoginCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAuth(context: Context) : Auth(context){
    override val SCOPE : String? = "user";
    override val LOGIN_REDIRECT : Class<*> = LoginActivity::class.java;
    override val IS_AUTH_REDIRECT : Class<*> = MainActivity::class.java;

    override fun login(email: String, password: String, callback : LoginCallback) {
        val retrofit = BaseAPIBuilder().retrofit
        val loginApiService = retrofit.create(LoginApiService::class.java)
        val call = loginApiService.login(email, password)

        call.enqueue(object : Callback<LoginApiResponse> {
            override fun onResponse(call: Call<LoginApiResponse>, response: Response<LoginApiResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody!!.user.role == SCOPE){
                        setToken(responseBody!!.token); // dri API
                        setProfile(
                            responseBody!!.user.name,
//                            responseBody!!.user.nim,
                            1234567890.toString(),
                            responseBody!!.user.email
                        );
                        callback.onLoginSuccess()
                    }
                } else {
                    val responseBody = response.body()
                    Log.d("Failed request", response.toString());
                    callback.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<LoginApiResponse>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
                callback.onLoginFailure()
            }
        })
    }

    override fun deleteProfile(){
        val editor = getStorage().edit();
        editor.remove("nama");
        editor.remove("nim");
        editor.remove("email");
        editor.apply();
    }

    fun setProfile( // sesuain ama metadata user nanti, tambahin lg getter nya
        nama : String,
        nim : String,
        email : String,
    ){
        val editor = getStorage().edit();
        editor.putString("nama", nama);
        editor.putString("nim", nim);
        editor.putString("email", email);
        editor.apply();
    }



    fun getNama() : String? {
        return getStorage().getString("nama", null);
    }

    fun getNim() : String? {
        return getStorage().getString("nim", null);
    }

    fun getEmail() : String? {
        return getStorage().getString("email", null);
    }

}