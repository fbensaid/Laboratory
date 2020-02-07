package com.mtp.laboproject.data.remoteApi

import com.mtp.laboproject.data.model.ForgottenPasswordResponse
import com.mtp.laboproject.data.model.alert.AlertsResponse
import com.mtp.laboproject.global.Constants
import com.mtp.laboproject.data.model.labs.LaboListResponse
import com.mtp.laboproject.data.model.labs.UserLabsWithError
import com.mtp.laboproject.data.model.statistics.StatisticsResponse
import com.mtp.laboproject.data.model.user.UserLoginResponse
import com.mtp.laboproject.global.Constants.Variants.EMAIL
import com.mtp.laboproject.global.Constants.Variants.FIREBASE_TOKEN
import com.mtp.laboproject.global.Constants.Variants.PASSWORD
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET(Constants.EndPoints.LABORATORY_ENDPOINT)
    fun getLaboList(): Deferred<Response<LaboListResponse>>


    @GET(Constants.EndPoints.FORGOTTEN_PASSWORD_ENDPOINT)
    fun forgotPassword(email : String): Deferred<Response<ForgottenPasswordResponse>>

    @GET(Constants.EndPoints.ALERTS_ENDPOINT)
    fun getAlerts(): Single<AlertsResponse>

    @POST(Constants.EndPoints.LOGIN_ENDPOINT)
    @FormUrlEncoded
    fun login(@Field(EMAIL)email:String, @Field(PASSWORD)password:String,@Field(FIREBASE_TOKEN)firebase:String): Deferred<Response<UserLoginResponse>>

    @GET(Constants.EndPoints.STATISTICS_ENDPOINT)
    fun getStatistics(): Deferred<Response<StatisticsResponse>>

}