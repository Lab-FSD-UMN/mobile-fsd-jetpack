package com.example.mobile_fsd_jetpack.auth


import android.content.Context
import android.content.SharedPreferences
import com.example.mobile_fsd_jetpack.api.utils.LoginCallback

abstract class Auth (private val context : Context){

    abstract val SCOPE : String?;
    abstract val LOGIN_REDIRECT : Class<*>;
    abstract val IS_AUTH_REDIRECT : Class<*>;
    abstract fun login(email : String, password : String, callback : LoginCallback);
    abstract fun deleteProfile();

    private val TAG = "token";

    private val storage = context.getSharedPreferences(SCOPE, Context.MODE_PRIVATE)
    protected fun setToken(token : String) {
        val editor = storage.edit();
        editor.putString(TAG, token);
        editor.apply();
    }

    fun getStorage() : SharedPreferences{
        return storage;
    }

    fun getToken(): String? {
        return storage.getString(TAG, null);
    }

    fun revokeToken(){
        val editor = storage.edit();
        editor.remove(TAG);
        editor.apply();
        this.deleteProfile();
    }

    fun authenticated() : Boolean {
//        return false
        return this.getToken() != null;
    }

}