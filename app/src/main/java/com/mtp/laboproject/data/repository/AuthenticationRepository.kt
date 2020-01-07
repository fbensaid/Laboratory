package com.mtp.laboproject.data.repository

import com.mtp.laboproject.data.model.UserResponse


class  AuthenticationRepository  : BaseRepository() {

     fun storeUser(user: UserResponse) {
         sharedPreferences.apply {
             userResponse=user
             isStillConnected=true
         }
     }

     fun storeAuthData(mail: String, pass: String, isConnected: Boolean, finger: Boolean) {
        sharedPreferences.apply {
            email = mail
            password = pass
            isConnectedSuccessBefore = isConnected
            fingerPrint = finger
        }
     }


}