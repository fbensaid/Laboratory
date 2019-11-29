package com.mtp.laboproject.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtp.laboproject.data.model.LaboratoryListResponse
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
    //initialize news repo
    private val labsRepository: LaboratoryRepository = LaboratoryRepository(Apifactory.Api)
    //live data that will be populated as news updates
    val labsLiveData = MutableLiveData<MutableList<LaboratoryListResponse>>()

    fun getLabs() {
        ///launch the coroutine scope
        scope.launch {
            //get latest news from news repo
            val latestNews = labsRepository.getLabs()
            //post the value inside live data
            labsLiveData.postValue(latestNews)

        }
    }

    fun cancelRequests() = coroutineContext.cancel()


    override fun onCleared() {
        super.onCleared()

        this.parentJob.cancel()
    }
}