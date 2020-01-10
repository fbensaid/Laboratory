package com.mtp.laboproject.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtp.laboproject.data.remoteApi.Apifactory
import com.mtp.laboproject.data.repository.AlertsRepository
import com.mtp.laboproject.data.model.AlertsDetailsResponse
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
    val alertsLiveData = MutableLiveData<MutableList<AlertsDetailsResponse>>()

    fun getAlerts() {
        ///launch the coroutine scope
        scope.launch {
            //get latest news from news repo
            var latestAlerts = alertRepository.getAlerts()
            //latestAlerts = mutableListOf<AlertsDetailsResponse>()
            //post the value inside live data
            alertsLiveData.postValue(latestAlerts)

        }
    }

    fun cancelRequests() = coroutineContext.cancel()


    override fun onCleared() {
        super.onCleared()

        this.parentJob.cancel()
    }
}