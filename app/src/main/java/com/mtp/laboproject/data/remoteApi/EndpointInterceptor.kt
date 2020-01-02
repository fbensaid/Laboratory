package com.mtp.laboproject.data.remoteApi

import com.mtp.laboproject.global.AppUtils
import com.mtp.laboproject.global.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class EndpointInterceptor :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().toString()
        if (url.contains(Constants.BASE_URL)) {
            if (AppUtils().isNetworkAvailable()) {
//                if (mPreferences.isConnected()) {
                    request = request.newBuilder()
                       .method(request.method(), request.body())
   //                    .addHeader("Authorization", "Bearer " + mPreferences.getToken())
                        .build()
//                }
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
