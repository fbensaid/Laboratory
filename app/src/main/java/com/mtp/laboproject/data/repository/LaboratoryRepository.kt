package com.mtp.laboproject.data.repository


import androidx.lifecycle.MutableLiveData
import com.mtp.laboproject.data.model.LaboratoryListResponse
import com.mtp.laboproject.data.remoteApi.ApiInterface


class LaboratoryRepository(private val api: ApiInterface) : BaseRepository() {

    //get latest news using safe api call
    suspend fun getLabs() :  MutableList<LaboratoryListResponse>?{
        return safeApiCall(
            //await the result of deferred type
            call = {api.getLaboList().await()},
            error = "Error fetching news"
            //convert to mutable list
        )?.toMutableList()
    }


}