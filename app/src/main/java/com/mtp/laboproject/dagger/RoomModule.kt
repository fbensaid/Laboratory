package com.farouk.travelcar.dagger

import android.app.Application
import dagger.Provides
import androidx.room.Room
import com.farouk.travelcar.data.repository.UserRepository
import com.mtp.laboproject.data.Database.AppDataBase
import com.mtp.laboproject.data.dao.UserDao
import dagger.Module
import javax.inject.Singleton


@Module
class RoomModule(mApplication: Application) {

    private val appDatabase: AppDataBase =
        Room.databaseBuilder(mApplication, AppDataBase::class.java!!, "demo-db").build()

    @Singleton
    @Provides
    internal fun providesRoomDatabase(): AppDataBase {
        return appDatabase
    }

    @Singleton
    @Provides
    internal fun providesUserDao(demoDatabase: AppDataBase): UserDao {
        return demoDatabase.getUserDao()
    }

    @Singleton
    @Provides
    internal fun UserRepository(productDao: UserDao): UserRepository {
        return UserRepository(productDao)
    }

}