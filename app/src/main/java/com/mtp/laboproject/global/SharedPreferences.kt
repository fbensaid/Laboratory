package com.mtp.laboproject.global


import android.content.Context
import android.text.TextUtils

import com.google.gson.Gson
import com.mtp.laboproject.data.model.UserResponse
import com.securepreferences.SecurePreferences

class SharedPreferences(context: Context) {

    val isConnected: Boolean
        get() = !TextUtils.isEmpty(token)

    var isFirstInstall: String?
        get() = mSharedPreferences.getString(FIRST_INSTALL_FLAG, null)
        set(value) {
            val editor = mSharedPreferences.edit()
            editor.putString(FIRST_INSTALL_FLAG, value)
            editor.commit()
        }

    var token: String?
        get() = mSharedPreferences.getString(TOKEN_FLAG, null)
        set(token) {
            val editor = mSharedPreferences.edit()
            editor.putString(TOKEN_FLAG, token)
            editor.commit()
        }

    var email: String?
        get() = mSharedPreferences.getString(EMAIL_FLAG, null)
        set(email) {
            val editor = mSharedPreferences.edit()
            editor.putString(EMAIL_FLAG, email)
            editor.apply()
        }
    var password: String?
        get() = mSharedPreferences.getString(PASSWORD_FLAG, null)
        set(email) {
            val editor = mSharedPreferences.edit()
            editor.putString(PASSWORD_FLAG, email)
            editor.apply()
        }


    var userResponse: UserResponse
        get() {
            val gson = Gson()
            val json = mSharedPreferences.getString(USER_FLAG, null)
            return gson.fromJson<UserResponse>(json, UserResponse::class.java!!)
        }
        set(user) {
            val editor = mSharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(user)
            editor.putString(USER_FLAG, json)
            editor.apply()
        }



    var model: String?
        get() = mSharedPreferences.getString(MODEL_FLAG, null)
        set(model) {
            val editor = mSharedPreferences.edit()
            editor.putString(MODEL_FLAG, model)
            editor.commit()
        }


    init {
        mSharedPreferences = SecurePreferences(context, SECRET_KEY, FILE_NAME_FLAG)
    }




    fun clearSharedPreferences() {
        token = null
        val editor = mSharedPreferences.edit()
        editor.clear()
        editor.commit()
    }



    companion object {

        private val USER_FLAG = "user_flag"
        private val TOKEN_FLAG = "token_flag"
        private val FIRST_INSTALL_FLAG = "first_install_flag"
        private val MODEL_FLAG = "model_flag"
        private val EMAIL_FLAG = "email_flag"
        private val PASSWORD_FLAG = "password_flag"
        private lateinit var mSharedPreferences: android.content.SharedPreferences

        private val FILE_NAME_FLAG = "bingo_file.xml"
        private val SECRET_KEY = "secret_bingo_key"

        operator fun set(key: String, value: String) {
            val editor = mSharedPreferences.edit()
            editor.putString(key, value)
            editor.commit()
        }
    }
}
