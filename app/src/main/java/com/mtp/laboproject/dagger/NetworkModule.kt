package com.mtp.laboproject.dagger

import android.content.Context
import com.mtp.laboproject.data.remoteApi.EndpointInterceptor
import com.mtp.laboproject.global.SharedPreferences
import dagger.Module
import dagger.Provides


import java.io.File
import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by MacBook on 8/28/17.
 */

@Module(includes = [ ContextModule::class, PreferencesModule::class ])
class NetworkModule {

    private val TAG = javaClass.simpleName

    @Provides
    @ApplicationScope
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
          print(
                message
            )
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @ApplicationScope
    fun endpointInterceptor(preferences: SharedPreferences, @ApplicationContext context: Context): EndpointInterceptor {
        return EndpointInterceptor(preferences, context)
    }

    @Provides
    @ApplicationScope
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, (10 * 1000 * 1000).toLong()) //10MB Cache
    }

    @Provides
    @ApplicationScope
    fun cacheFile(@ApplicationContext context: Context): File {
        return File(context.cacheDir, "okhttp_cache")
    }

    @Provides
    @ApplicationScope
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        endpointInterceptor: EndpointInterceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS).addInterceptor(loggingInterceptor)
            .addInterceptor(endpointInterceptor).cache(cache).build()
    }


    @Provides
    @PicassoClientScope
    @ApplicationScope
    fun okHttpPicassoClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS).cache(cache).build()
    }

    companion object {

        private val CONNECT_TIMEOUT = 15
        private val WRITE_TIMEOUT = 15
        private val READ_TIMEOUT = 15
    }
}
