package com.mtp.laboproject.data.remoteApi

import com.mtp.laboproject.data.global.Constants
import com.mtp.laboproject.data.model.LaboratoryResponse
import com.mtp.laboproject.data.model.LabsList
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {


    @GET(Constants.EndPoints.LABORATORY)
    //suspend fun getLabo(): Response<List<LaboratoryResponse>>
    fun getLabo(): Deferred<Response<LabsList>>



}