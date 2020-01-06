package com.mtp.laboproject.data.repository


import com.mtp.laboproject.data.remoteApi.ApiInterface
import com.mtp.laboproject.data.rest.AlertsDetailsResponse


class AlertsRepository(private val api: ApiInterface) : BaseRepository() {

    //get latest news using safe api call
    suspend fun getAlerts(): MutableList<AlertsDetailsResponse>? {
        return safeApiCall(
            //await the result of deferred type
            call = { api.getAlerts().await() },
            error = "Error fetching news"
            //convert to mutable list
        )?.toMutableList()
    }
}