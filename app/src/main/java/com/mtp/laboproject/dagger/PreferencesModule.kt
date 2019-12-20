package com.mtp.laboproject.dagger

import android.app.Application
import android.content.Context
import dagger.Provides
import com.mtp.laboproject.global.SharedPreferences
import dagger.Module
import javax.inject.Singleton


@Module
class PreferencesModule(mApplication: Application) {
    private val mContext: Context = mApplication.applicationContext

    @Singleton
    @Provides
    fun sharedPreferences(): SharedPreferences {
        return SharedPreferences(mContext)
    }

}