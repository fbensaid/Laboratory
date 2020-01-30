package com.mtp.laboproject.data.repository

import com.mtp.laboproject.data.model.statistics.StatisticsResponse
import com.mtp.laboproject.data.remoteApi.ApiInterface


class StatisticsRepository(private val api: ApiInterface) : BaseRepository() {

    //get latest news using safe api call
    suspend fun getStatistics(): Any? {
        return safeApiCall(
            //await the result of deferred type
            call = { api.getStatistics().await() },
            error = "Error fetching news"
        )
    }
}