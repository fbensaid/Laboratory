package com.mtp.laboproject.global


import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.mtp.laboproject.data.model.user.UserLoginResponse
import com.securepreferences.SecurePreferences

class SharedPreferences(context: Context) {


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
        set(password) {
            val editor = mSharedPreferences.edit()
            editor.putString(PASSWORD_FLAG, password)
            editor.apply()
        }
    var fingerPrint: Boolean?
        get() = mSharedPreferences.getBoolean(FINGER_FLAG, false)
        set(fingerPrint) {
            val editor = mSharedPreferences.edit()
            if (fingerPrint != null) {
                editor.putBoolean(FINGER_FLAG, fingerPrint)
            }
            editor.apply()
        }
    var notification: Boolean?
        get() = mSharedPreferences.getBoolean(NOTIF_FLAG, true)
        set(notification) {
            val editor = mSharedPreferences.edit()
            if (notification != null) {
                editor.putBoolean(NOTIF_FLAG, notification)
            }
            editor.apply()
        }
    var isConnectedSuccessBefore: Boolean
        get() = mSharedPreferences.getBoolean(CONNECTED_FLAG, false)
        set(isConnectedSuccessBefore) {
            val editor = mSharedPreferences.edit()
            if (isConnectedSuccessBefore != null) {
                editor.putBoolean(CONNECTED_FLAG, isConnectedSuccessBefore)
            }
            editor.apply()
        }

    var isStillConnected: Boolean
        get() = mSharedPreferences.getBoolean(IS_STILL_CONNECTED_FLAG, false)
        set(isStillConnected) {
            val editor = mSharedPreferences.edit()
            if (isStillConnected != null) {
                editor.putBoolean(IS_STILL_CONNECTED_FLAG, isStillConnected)
            }
            editor.apply()
        }


    var userResponse: UserLoginResponse
        get() {
            val gson = Gson()
            val json = mSharedPreferences.getString(USER_FLAG, null)
            return gson.fromJson<UserLoginResponse>(json, UserLoginResponse::class.java!!)
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
        private val FINGER_FLAG = "finger_flag"
        private val NOTIF_FLAG = "notif_flag"
        private val CONNECTED_FLAG = "connect_flag"
        private val IS_STILL_CONNECTED_FLAG = "is_still_connect_flag"


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
