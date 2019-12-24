package com.mtp.laboproject.dagger


import android.app.Application
import com.farouk.travelcar.dagger.RoomModule
import com.farouk.travelcar.data.repository.UserRepository
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.data.Database.AppDataBase
import com.mtp.laboproject.data.repository.BaseRepository
import com.mtp.laboproject.global.FirebaseMessagingService
import com.mtp.laboproject.global.SharedPreferences
import com.mtp.laboproject.view.ui.activity.AuthentificationActivity
import com.mtp.laboproject.view.ui.activity.BaseActivity
import com.mtp.laboproject.view.ui.activity.MainActivity
import com.mtp.laboproject.view.ui.fragment.BaseFragment
import com.mtp.laboproject.view.ui.fragment.ProfilFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, RoomModule::class, PreferencesModule::class,APIClientModule::class,PicassoModule::class])
interface AppComponent {


    fun inject(baseActivity: BaseActivity)
    fun inject(baseFragment: BaseFragment)
    fun inject(baseRepository: BaseRepository)
    fun inject(appComponent: LaboApplication)
    fun inject(fireBase: FirebaseMessagingService)
    fun appDatabase(): AppDataBase
    fun application(): Application
    fun getPreferences(): SharedPreferences


}