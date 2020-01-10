package com.mtp.laboproject.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mtp.laboproject.data.model.UserResponse
import com.mtp.laboproject.data.repository.AuthenticationRepository
import com.mtp.laboproject.global.SharedPreferences
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class ProfilViewModel : ViewModel() {
    //create a new Job
    private val parentJob = Job()
    //create a coroutine context with the job and the dispatcher
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    //create a coroutine scope with the coroutine context
     val scope = CoroutineScope(coroutineContext)

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Couroutine", "Caught $exception")
    }
    //initialize news repo
    private val authRepository: AuthenticationRepository = AuthenticationRepository()
    //live data that will be populated as news updates


    fun getsharedPreference():SharedPreferences{
        return authRepository.sharedPreferences
    }


        override fun onCleared() {
        super.onCleared()
    }
}
