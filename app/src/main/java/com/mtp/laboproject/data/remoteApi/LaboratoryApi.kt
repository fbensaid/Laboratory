package com.mtp.laboproject.data.remoteApi

import com.mtp.laboproject.data.model.LaboratoryResponse

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface LaboratoryApi{

@GET("movies")
suspend fun getLabo(): Response<List<LaboratoryResponse>>
    @GET("lab/list")
    suspend fun getLaboList(): Response<List<LaboratoryResponse>>



    companion object {
        operator fun invoke(): LaboratoryApi {

            return Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create())
                //.baseUrl("https://api.simplifiedcoding.in/course-apis/recyclerview/")
                .baseUrl("http://172.16.1.12:3000/api/").build()
                .create(LaboratoryApi::class.java)
        }
    }
}