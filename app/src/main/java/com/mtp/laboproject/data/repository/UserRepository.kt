package com.farouk.travelcar.data.repository

import androidx.lifecycle.LiveData
import com.mtp.laboproject.data.dao.UserDao
import com.mtp.laboproject.data.model.UserResponse
import com.mtp.laboproject.data.repository.BaseRepository

class UserRepository( private val userDao: UserDao) : BaseRepository() {

     fun getUser(): LiveData<UserResponse> {
        return userDao.getUser()
    }

      fun insert(userResponse: UserResponse):Long {
        return userDao.insertUser(userResponse)
    }

}