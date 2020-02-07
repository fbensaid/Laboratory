package com.mtp.laboproject.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtp.laboproject.data.remoteApi.Apifactory
import com.mtp.laboproject.data.repository.AlertsRepository
import com.mtp.laboproject.data.model.AlertsDetailsResponse
import com.mtp.laboproject.data.model.UserAlertsWithError
import com.mtp.laboproject.data.model.error.ErrorServerResponse
import com.mtp.laboproject.data.model.labs.LaboListResponse
import com.mtp.laboproject.data.model.user.UserLabsWithError
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AlertsViewModel : ViewModel() {

    //create a new Job
    private val parentJob = Job()
    //create a coroutine context with the job and the dispatcher
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    //create a coroutine scope with the coroutine context
    private val scope = CoroutineScope(coroutineContext)
    //initialize news repo
    private val alertRepository: AlertsRepository = AlertsRepository(Apifactory.Api)
    //live data that will be populated as news updates

    val alertsLiveData = MutableLiveData<UserAlertsWithError>()
    private var alertList = UserAlertsWithError(null, null)

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Couroutine", "Caught $exception")
    }

    fun getAlerts() {
        scope.launch(handler) {
            var labsResponse = alertRepository.getAlerts()
            if(labsResponse is ErrorServerResponse){
                alertList!!.serverErrorResponse=labsResponse
            }else if(labsResponse is AlertsDetailsResponse){
                alertList!!.userLabsResponse=labsResponse
            }
            alertsLiveData.postValue(alertList)
        }
    }


    fun cancelRequests() = coroutineContext.cancel()


    override fun onCleared() {
        super.onCleared()

        this.parentJob.cancel()
    }
}