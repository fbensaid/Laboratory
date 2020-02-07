/*
 * Copyright (c) 2019 - Crédit Agricole Payments & Services - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * This file can not be copied and/or distributed without the express permission of Crédit Agricole Payments & Services
 */

package com.mtp.laboproject

import android.app.Application
import android.content.Context
import com.mtp.laboproject.dagger.AppComponent
import com.mtp.laboproject.dagger.AppModule
import com.mtp.laboproject.dagger.RoomModule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.mtp.laboproject.dagger.DaggerAppComponent
import com.mtp.laboproject.dagger.PreferencesModule


class LaboApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var instance: Context
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        auth = FirebaseAuth.getInstance()
        FirebaseMessaging.getInstance().subscribeToTopic("ALL")
        initDI()
    }

    fun getAppContext(): Context {
        return instance
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomModule(RoomModule(this))
            .preferencesModule(PreferencesModule(this))
            .build()
    }
}


