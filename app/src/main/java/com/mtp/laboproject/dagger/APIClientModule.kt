package com.mtp.laboproject.dagger

import com.google.gson.Gson
import com.mtp.laboproject.BuildConfig
import com.mtp.laboproject.global.Constants
import dagger.Module
import dagger.Provides

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by MacBook on 8/28/17.
 */

@Module(includes = [NetworkModule::class])
class APIClientModule {

    @Provides
    @ApplicationScope
    fun apiClient(retrofit: Retrofit): APIClientModule {
        return retrofit.create<APIClientModule>(APIClientModule::class.java!!)
    }

    @Provides
    @ApplicationScope
    fun gson(): Gson {
        return Gson()
    }

    @Provides
    @ApplicationScope
    fun retrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
    }
}
