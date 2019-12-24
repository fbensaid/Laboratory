package com.mtp.laboproject.data.remoteApi

import com.mtp.laboproject.data.model.ForgottenPasswordResponse
import com.mtp.laboproject.global.Constants
import com.mtp.laboproject.data.model.LaboratoryListResponse

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET(Constants.EndPoints.LABORATORY_ENDPOINT)
    fun getLaboList(): Deferred<Response<List<LaboratoryListResponse>>>


    @GET(Constants.EndPoints.FORGOTTEN_PASSWORD_ENDPOINT)
    fun forgotPassword(email : String): Deferred<Response<ForgottenPasswordResponse>>




}