package com.mtp.laboproject.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtp.laboproject.data.model.labs.LabsListResponse
import com.mtp.laboproject.data.model.statistics.StatisticsResponse
import com.mtp.laboproject.data.remoteApi.Apifactory
import com.mtp.laboproject.data.repository.LaboratoryRepository
import com.mtp.laboproject.data.repository.StatisticsRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeViewModel : ViewModel() {
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Couroutine", "Caught $exception")
    }
    private val statRepository: StatisticsRepository = StatisticsRepository(Apifactory.Api)
     val statisticsLiveData = MutableLiveData<StatisticsResponse>()

    fun getStatistic() {
        scope.launch(handler) {
            val latestNews = statRepository.getStatistics()
            statisticsLiveData.postValue(latestNews)
        }
    }

    override fun onCleared() {
        super.onCleared()
        this.parentJob.cancel()
    }
}