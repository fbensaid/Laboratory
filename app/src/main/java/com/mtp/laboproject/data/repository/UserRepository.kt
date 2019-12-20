package com.farouk.travelcar.data.repository

import androidx.lifecycle.LiveData
import com.mtp.laboproject.data.model.UserResponse


interface UserRepository  {

      fun insert(userResponse: UserResponse):Long

     fun getUser(): LiveData<UserResponse>



}