package com.mtp.laboproject.data.remoteApi


import android.content.Context
import com.mtp.laboproject.dagger.ApplicationContext
import com.mtp.laboproject.global.AppUtils
import com.mtp.laboproject.global.Constants
import com.mtp.laboproject.global.SharedPreferences

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by MacBook on 8/28/17.
 */

class EndpointInterceptor(private val mPreferences: SharedPreferences, @param:ApplicationContext private val mContext: Context) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().toString()
        if (url.contains(Constants.BASE_URL)) {
            if (AppUtils.isNetworkAvailable(mContext)) {
               /* if (mPreferences.isConnected()) {
                    request = request.newBuilder()
                        .method(request.method(), request.body())
                        .addHeader("Authorization", "Bearer " + mPreferences.getToken())
                        .build()
                }*/
            } else {
                throw IOException("No network available")
            }


        }

        return chain.proceed(request)
    }

    companion object {

        private val TAG = EndpointInterceptor::class.java.simpleName
    }
}
