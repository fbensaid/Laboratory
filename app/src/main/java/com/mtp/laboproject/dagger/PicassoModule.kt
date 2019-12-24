package com.mtp.laboproject.dagger

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module(includes = [ContextModule::class, NetworkModule::class])
class PicassoModule {

    @Provides
    @ApplicationScope
    fun picasso(@ApplicationContext context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context).loggingEnabled(true).downloader(okHttp3Downloader).build()
    }

    @Provides
    @ApplicationScope
    fun okHttp3Downloader(@PicassoClientScope okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }

}
