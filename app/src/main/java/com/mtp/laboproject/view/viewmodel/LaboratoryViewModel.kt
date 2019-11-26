package com.mtp.laboproject.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtp.laboproject.Utils.Coroutine
import com.mtp.laboproject.data.repository.LaboratoryRepository
import com.mtp.laboproject.data.rest.LaboratoryResponse
import kotlinx.coroutines.Job

class LaboratoryViewModel(
    private val laboratoryRepository: LaboratoryRepository
) : ViewModel() {


    private lateinit var job:Job
    private val _laboratoryMutibaleLiveData=MutableLiveData<List<LaboratoryResponse>>()
    val laboratory:LiveData<List<LaboratoryResponse>>
    get() =_laboratoryMutibaleLiveData

     fun getLaboratory(){
        job=Coroutine.ioThenMain(
            {laboratoryRepository.getLaboratory()},
            {_laboratoryMutibaleLiveData.value=it}
        )
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized)
            job.cancel()
    }
}
