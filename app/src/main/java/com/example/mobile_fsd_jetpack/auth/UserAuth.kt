package com.example.mobile_fsd_jetpack.auth

import android.content.Context
import android.util.Log
import com.example.mobile_fsd_jetpack.LoginActivity
import com.example.mobile_fsd_jetpack.MainActivity

class UserAuth(context: Context) : Auth(context){
    override val SCOPE : String? = "user";
    override val LOGIN_REDIRECT : Class<*> = LoginActivity::class.java;
    override val IS_AUTH_REDIRECT : Class<*> = MainActivity::class.java;

    override fun login(email: String, password: String): Boolean  {
        // nanti call API
        if (email == "taratakdung@gmail.com" && password == "12345678"){
            this.setToken("taratakdung"); // dri API
            this.setProfile("FarrelD123", "55702", email); // dari API
            return true;
        }
        return false;
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