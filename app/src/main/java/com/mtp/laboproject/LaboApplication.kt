/*
 * Copyright (c) 2019 - Crédit Agricole Payments & Services - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * This file can not be copied and/or distributed without the express permission of Crédit Agricole Payments & Services
 */

package com.mtp.laboproject

import android.app.Application
import android.content.Context


class LaboApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this


    }

    override fun onTerminate() {
        super.onTerminate()
    }

    companion object {
        lateinit var instance: Application


        fun getAppContext(): Context {
            return instance.applicationContext
        }
    }
}