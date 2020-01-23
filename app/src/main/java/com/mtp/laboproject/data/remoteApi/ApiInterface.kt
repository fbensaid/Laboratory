package com.mtp.laboproject.data.remoteApi

import com.mtp.laboproject.data.model.AlertsDetailsResponse
import com.mtp.laboproject.data.model.ForgottenPasswordResponse
import com.mtp.laboproject.global.Constants
import com.mtp.laboproject.data.model.labs.LabsListResponse
import com.mtp.laboproject.data.model.statistics.StatisticsResponse
import com.mtp.laboproject.data.model.user.UserLoginResponse
import com.mtp.laboproject.global.Constants.Variants.EMAIL
import com.mtp.laboproject.global.Constants.Variants.PASSWORD
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET(Constants.EndPoints.LABORATORY_ENDPOINT)
    fun getLaboList(): Deferred<Response<LabsListResponse>>


    @GET(Constants.EndPoints.FORGOTTEN_PASSWORD_ENDPOINT)
    fun forgotPassword(email : String): Deferred<Response<ForgottenPasswordResponse>>

    @GET(Constants.EndPoints.ALERTS_ENDPOINT)
    fun getAlerts(): Deferred<Response<List<AlertsDetailsResponse>>>

    @POST(Constants.EndPoints.LOGIN_ENDPOINT)
    @FormUrlEncoded
    fun login(@Field(EMAIL)email:String, @Field(PASSWORD)password:String): Deferred<Response<UserLoginResponse>>

    @GET(Constants.EndPoints.STATISTICS_ENDPOINT)
    fun getStatistics(): Deferred<Response<StatisticsResponse>>

}