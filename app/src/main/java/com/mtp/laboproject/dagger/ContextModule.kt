package com.mtp.laboproject.dagger

import android.content.Context

import dagger.Module
import dagger.Provides


/**
 * Created on 2/2/18.
 */

@Module
class ContextModule(context: Context) {

    private val mContext: Context

    init {
        mContext = context.applicationContext
    }

    @Provides
    @ApplicationScope
    fun context(): Context {
        return mContext
    }
}
