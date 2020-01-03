package com.mtp.laboproject.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtp.laboproject.data.model.LaboratoryListResponse
import com.mtp.laboproject.data.model.UserResponse
import com.mtp.laboproject.data.remoteApi.Apifactory
import com.mtp.laboproject.data.repository.AuthenticationRepository
import com.mtp.laboproject.data.repository.LaboratoryRepository
import com.mtp.laboproject.global.SharedPreferences
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AuthViewModel : ViewModel() {

    //initialize news repo
    private val authRepository: AuthenticationRepository = AuthenticationRepository()
    //live data that will be populated as news updates


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
