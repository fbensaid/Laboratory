package com.mtp.laboproject.data.repository

import androidx.lifecycle.LiveData
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.data.model.UserResponse
import com.mtp.laboproject.data.repository.BaseRepository
import com.mtp.laboproject.global.SharedPreferences
import javax.inject.Inject


class  AuthenticationRepository  : BaseRepository() {

     fun storeUser(userResponse: UserResponse) {
         sharedPreferences.userResponse=userResponse
     }

     fun storeAuthData(mail: String, pass: String, isConnected: Boolean, finger: Boolean) {
        sharedPreferences.apply {
            email = mail
            password = pass
            isConnectedSuccess = isConnected
            fingerPrint = finger
        }
     }


}