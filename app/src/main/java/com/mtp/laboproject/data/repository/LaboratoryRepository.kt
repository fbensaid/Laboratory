package com.mtp.laboproject.data.repository

import com.mtp.laboproject.data.remoteApi.LaboratoryApi
import com.mtp.laboproject.data.remoteApi.SafeLaboratoryRequestApi

class LaboratoryRepository (
    private val api: LaboratoryApi
): SafeLaboratoryRequestApi(){

    suspend fun getLaboratory() = apiRequest { api.getLabo() }

}