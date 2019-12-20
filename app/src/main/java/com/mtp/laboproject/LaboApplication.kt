/*
 * Copyright (c) 2019 - Crédit Agricole Payments & Services - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * This file can not be copied and/or distributed without the express permission of Crédit Agricole Payments & Services
 */

package com.mtp.laboproject

import android.app.Application
import com.mtp.laboproject.dagger.AppComponent
import com.mtp.laboproject.dagger.AppModule
import com.farouk.travelcar.dagger.RoomModule
import com.mtp.laboproject.dagger.DaggerAppComponent
import com.mtp.laboproject.dagger.PreferencesModule


class LaboApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomModule(RoomModule(this))
            .preferencesModule(PreferencesModule(this))
            .build()
    }
}