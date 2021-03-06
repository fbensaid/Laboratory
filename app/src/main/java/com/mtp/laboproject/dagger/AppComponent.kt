package com.mtp.laboproject.dagger


import android.app.Application
import com.mtp.laboproject.view.ui.fragment.BaseFragment
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.data.repository.BaseRepository
import com.mtp.laboproject.global.FirebaseMessagingService
import com.mtp.laboproject.global.SharedPreferences
import com.mtp.laboproject.view.ui.activity.BaseActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, RoomModule::class, PreferencesModule::class])
interface AppComponent {


    fun inject(baseActivity: BaseActivity)
    fun inject(baseFragment: BaseFragment)
    fun inject(baseRepository: BaseRepository)
    fun inject(appComponent: LaboApplication)

    //fun appDatabase(): AppDataBase

    fun inject(fireBase: FirebaseMessagingService)

    fun application(): Application
    fun getPreferences(): SharedPreferences

}