package com.mtp.laboproject.data.remoteApi

import com.mtp.laboproject.data.rest.LaboratoryResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface LaboratoryApi{

@GET("movies")
suspend fun getLabo(): Response<List<LaboratoryResponse>>



    companion object {
        operator fun invoke(): LaboratoryApi {

            return Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.simplifiedcoding.in/course-apis/recyclerview/")
                .build()
                .create(LaboratoryApi::class.java)
        }
    }
}