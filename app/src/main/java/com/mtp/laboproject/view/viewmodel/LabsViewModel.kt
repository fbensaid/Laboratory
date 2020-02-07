package com.mtp.laboproject.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtp.laboproject.data.model.error.ErrorServerResponse
import com.mtp.laboproject.data.model.labs.LaboListResponse
import com.mtp.laboproject.data.model.user.UserLabsWithError
import com.mtp.laboproject.data.model.user.UserLoginResponse
import com.mtp.laboproject.data.model.user.UserLoginWithError
import com.mtp.laboproject.data.remoteApi.Apifactory
import com.mtp.laboproject.data.repository.LaboratoryRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LabsViewModel : ViewModel() {
    //create a new Job
    private val parentJob = Job()
    //create a coroutine context with the job and the dispatcher
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    //create a coroutine scope with the coroutine context
    private val scope = CoroutineScope(coroutineContext)
    val loginLiveData = MutableLiveData<UserLabsWithError>()
    private var labsList = UserLabsWithError(null, null)

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Couroutine", "Caught $exception")
    }
    //initialize news repo
    private val labsRepository: LaboratoryRepository = LaboratoryRepository(Apifactory.Api)
    //live data that will be populated as news updates
    val labsLiveData = MutableLiveData<LaboListResponse>()


    fun getLabs() {
        scope.launch(handler) {
            var labsResponse = labsRepository.getLabs()
            if(labsResponse is ErrorServerResponse){
                labsList!!.serverErrorResponse=labsResponse
            }else if(labsResponse is LaboListResponse){
                labsList!!.userLabsResponse=labsResponse
            }
            loginLiveData.postValue(labsList)
        }
    }


    fun cancelRequests() = coroutineContext.cancel()


    override fun onCleared() {
        super.onCleared()
        this.parentJob.cancel()
    }
}