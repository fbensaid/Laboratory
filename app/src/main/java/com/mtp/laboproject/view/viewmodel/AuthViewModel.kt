package com.mtp.laboproject.view.viewmodel

import androidx.lifecycle.ViewModel
import com.mtp.laboproject.data.model.UserResponse
import com.mtp.laboproject.data.repository.AuthenticationRepository
import com.mtp.laboproject.global.SharedPreferences

class AuthViewModel : ViewModel() {

    //initialize news repo
    private val authRepository: AuthenticationRepository = AuthenticationRepository()
    //live data that will be populated as news updates


    fun getsharedPreference():SharedPreferences{
        return authRepository.sharedPreferences
    }

    fun storeAuthData(mail: String, pass: String, isConnected: Boolean, finger: Boolean) {
        authRepository.storeAuthData(mail,pass,isConnected,finger)

        }

    fun storeuser(user:UserResponse) {
        authRepository.storeUser(user)
    }



        override fun onCleared() {
        super.onCleared()
    }
}
