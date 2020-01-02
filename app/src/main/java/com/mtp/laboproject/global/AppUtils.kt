package com.mtp.laboproject.global

import android.content.Context
import android.net.ConnectivityManager

import androidx.annotation.Nullable

object AppUtils {

    fun isNetworkAvailable(@Nullable context: Context?): Boolean {
        if (context != null) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected

        }

        return false
    }




}
