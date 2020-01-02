package com.mtp.laboproject.global

import android.content.Context
import android.net.ConnectivityManager
import com.mtp.laboproject.LaboApplication

 class AppUtils {

    private val TAG = AppUtils::class.java.simpleName


    fun isNetworkAvailable(): Boolean {
        if (LaboApplication.instance != null) {
            val cm = LaboApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

        return false
    }
}