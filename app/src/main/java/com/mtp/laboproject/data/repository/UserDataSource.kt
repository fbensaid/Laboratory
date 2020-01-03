package com.farouk.travelcar.data.repository

import androidx.lifecycle.LiveData
import com.mtp.laboproject.data.dao.UserDao
import com.mtp.laboproject.data.model.UserResponse
import com.mtp.laboproject.data.repository.UserRepository

class UserDataSource( private val userDao: UserDao) : UserRepository {

    override fun getUser(): LiveData<UserResponse> {
        return userDao.getUser()
    }

    override  fun insert(userResponse: UserResponse):Long {
        return userDao.insertUser(userResponse)
    }

}