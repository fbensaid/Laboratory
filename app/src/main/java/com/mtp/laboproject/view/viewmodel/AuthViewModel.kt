package com.mtp.laboproject.view.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.mtp.laboproject.data.model.error.ErrorServerResponse
import com.mtp.laboproject.data.model.user.UserLoginResponse
import com.mtp.laboproject.data.model.user.UserLoginWithError
import com.mtp.laboproject.data.remoteApi.Apifactory
import com.mtp.laboproject.data.repository.AuthenticationRepository
import com.mtp.laboproject.global.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class AuthViewModel : ViewModel() {

    private var newsLiveData: LiveData<Any>? = null
    private var userLiveData: LiveData<UserLoginWithError>? = null

    var news: ObservableField<Any> = ObservableField<Any>()


    fun getObservableProject(): LiveData<Any>? {
        return newsLiveData
    }

    private var mApiResponse: MediatorLiveData<UserLoginWithError>? = null
    val data: MutableLiveData<UserLoginResponse> = MutableLiveData<UserLoginResponse>()
    var mutableTest = MutableLiveData<UserLoginWithError>()
    private var mApiRepo: Apifactory? = null
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Couroutine", "Caught $exception")
    }
    private val authRepository: AuthenticationRepository = AuthenticationRepository(Apifactory.Api)
    val loginLiveData = MutableLiveData<UserLoginWithError>()
    private var user = UserLoginWithError(null, null)

    /*fun getData(login: String, pass: String): LiveData<UserLoginWithError?>? {
        scope.launch(handler) {
            var loginResponse = authRepository.login(login, pass)
            if (loginResponse is ErrorServerResponse) {
                user!!.serverErrorResponse = loginResponse
            } else if (loginResponse is UserLoginResponse) {
                user!!.userLoginResponse = loginResponse
            }
             loginLiveData.postValue(user)
            /*mApiResponse = MediatorLiveData()
            mApiRepo = Apifactory
            mApiResponse!!.addSource<UserLoginWithError>(
                authRepository.login(login, pass)!!,
                object : Observer<UserLoginWithError?> {
                    override fun onChanged(@Nullable apiResponse: UserLoginWithError?) {
                        mApiResponse!!.value = apiResponse
                    }
                })
        }*/
        return mApiResponse

    }*/

        fun setLogin(login:String,pass: String) {
            scope.launch(handler) {
                var loginResponse = authRepository.login(login,pass)
                if(loginResponse is ErrorServerResponse){
                    user!!.serverErrorResponse=loginResponse
                }else if(loginResponse is UserLoginResponse){
                    user!!.userLoginResponse=loginResponse
                }
                loginLiveData.postValue(user)
            }
        }



        fun getsharedPreference(): SharedPreferences {
        return authRepository.sharedPreferences
    }

    fun storeAuthData(mail: String, pass: String, isConnected: Boolean, finger: Boolean) {
        authRepository.storeAuthData(mail, pass, isConnected, finger)
    }

    fun storeuser(user: UserLoginResponse) {
        authRepository.storeUser(user)
    }

    override fun onCleared() {
        super.onCleared()
    }
}
