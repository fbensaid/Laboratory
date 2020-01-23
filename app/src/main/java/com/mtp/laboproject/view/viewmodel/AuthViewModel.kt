package com.mtp.laboproject.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtp.laboproject.data.model.user.UserLoginResponse
import com.mtp.laboproject.data.remoteApi.Apifactory
import com.mtp.laboproject.data.repository.AuthenticationRepository
import com.mtp.laboproject.global.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AuthViewModel : ViewModel() {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Couroutine", "Caught $exception")
    }
    private val authRepository: AuthenticationRepository = AuthenticationRepository(Apifactory.Api)
    val loginLiveData = MutableLiveData<UserLoginResponse>()

    fun setLogin(login:String,pass: String) {
        scope.launch(handler) {
            val loginResponse = authRepository.login(login,pass)
            loginLiveData.postValue(loginResponse)
        }
    }

    fun getsharedPreference():SharedPreferences{
        return authRepository.sharedPreferences
    }

    fun storeAuthData(mail: String, pass: String, isConnected: Boolean, finger: Boolean) {
        authRepository.storeAuthData(mail,pass,isConnected,finger)
        }

    fun storeuser(user:UserLoginResponse) {
        authRepository.storeUser(user)
    }

        override fun onCleared() {
        super.onCleared()
    }
}
