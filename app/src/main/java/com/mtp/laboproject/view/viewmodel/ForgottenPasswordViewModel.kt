package com.mtp.laboproject.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtp.laboproject.data.model.ForgottenPasswordResponse
import com.mtp.laboproject.data.remoteApi.Apifactory
import com.mtp.laboproject.data.repository.ForgottenPasswordRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ForgottenPasswordViewModel : ViewModel() {

    //create a new Job
    private val parentJob = Job()
    //create a coroutine context with the job and the dispatcher
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    //create a coroutine scope with the coroutine context
    private val scope = CoroutineScope(coroutineContext)
    //initialize news repo
    private val forgotPasswordRepository: ForgottenPasswordRepository = ForgottenPasswordRepository(Apifactory.Api)
    //live data that will be populated as news updates
    val forgotPasswordLiveData = MutableLiveData<ForgottenPasswordResponse>()

    fun forgotPassword(email: String) {
        ///launch the coroutine scope
        scope.launch {
            //get latest news from news repo
            val latestNews = forgotPasswordRepository.forgotPassword(email)
            //post the value inside live data
            forgotPasswordLiveData.postValue(latestNews)

        }
    }

    fun cancelRequests() = coroutineContext.cancel()


    override fun onCleared() {
        super.onCleared()

        this.parentJob.cancel()
    }
}