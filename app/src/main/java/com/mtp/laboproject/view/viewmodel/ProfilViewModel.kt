package com.mtp.laboproject.view.viewmodel

import androidx.lifecycle.ViewModel
import com.mtp.laboproject.data.remoteApi.Apifactory
import com.mtp.laboproject.data.repository.AuthenticationRepository
import com.mtp.laboproject.global.SharedPreferences

class ProfilViewModel : ViewModel() {

    private val authRepository: AuthenticationRepository = AuthenticationRepository(Apifactory.Api)
    //live data that will be populated as news updates

    fun getsharedPreference():SharedPreferences{
        return authRepository.sharedPreferences
    }


        override fun onCleared() {
        super.onCleared()
    }
}
