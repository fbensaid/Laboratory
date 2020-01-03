package com.mtp.laboproject.data.repository

import androidx.lifecycle.LiveData
import com.mtp.laboproject.data.model.UserResponse
import com.mtp.laboproject.data.repository.BaseRepository


interface UserRepository  {

      fun insert(userResponse: UserResponse):Long

     fun getUser(): LiveData<UserResponse>



}