package com.mtp.laboproject.data.repository

import com.mtp.laboproject.data.model.labs.LaboListResponse
import com.mtp.laboproject.data.remoteApi.ApiInterface


class LaboratoryRepository(private val api: ApiInterface) : BaseRepository() {

    //get latest news using safe api call
    suspend fun getLabs(): Any? {
        return safeApiCall(
            //await the result of deferred type
            call = { api.getLaboList().await() },
            error = "Error fetching Labs"
        )
    }


}