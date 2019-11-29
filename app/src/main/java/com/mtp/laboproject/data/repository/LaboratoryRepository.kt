package com.mtp.laboproject.data.repository


import androidx.lifecycle.MutableLiveData
import com.mtp.laboproject.data.model.LaboratoryResponse
import com.mtp.laboproject.data.model.LabsList
import com.mtp.laboproject.data.remoteApi.ApiInterface

class LaboratoryRepository(private val api: ApiInterface) : BaseRepository() {


    /*suspend fun getLabos(): MutableLiveData<List<LaboratoryResponse>>? {

        //safeApiCall is defined in BaseRepository.kt (https://gist.github.com/navi25/67176730f5595b3f1fb5095062a92f15)
        val labsResponse = safeApiCall(
            call = { api.getLabo().await() },
            errorMessage = "Error Fetching Popular Movies"
        )

        val _laboratoryMutibaleLiveData = MutableLiveData<List<LaboratoryResponse>>()


        return _laboratoryMutibaleLiveData

    }*/

    //get latest news using safe api call
    suspend fun getLatestNews() :  MutableList<LaboratoryResponse>?{
        return safeApiCall(
            //await the result of deferred type
            call = {api.getLabo().await()},
            error = "Error fetching news"
            //convert to mutable list
        )?.labos?.toMutableList()
    }


}