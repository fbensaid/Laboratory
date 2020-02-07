package com.mtp.laboproject.data.repository

import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.data.model.error.ErrorServerResponse
import com.mtp.laboproject.data.remoteApi.Output
import com.mtp.laboproject.global.SharedPreferences

import retrofit2.Response
import javax.inject.Inject

open class BaseRepository {

    @Inject
    lateinit var sharedPreferences: SharedPreferences



    constructor() {
        LaboApplication.appComponent.inject(baseRepository = this)
    }


    suspend fun <T : Any> safeApiCall(call : suspend()-> Response<T>, error : String) :  Any?{
        val result = apiOutput(call, error)
        var output : Any? = null
        when(result){
            is Output.Success ->
                output = result.output

            is Output.Error -> {
                output = result.exception
            }
        }
        return output

    }
    private suspend fun<T : Any> apiOutput(call: suspend()-> Response<T> , error: String) : Output<Any>{
        val response = call.invoke()
        return if (response.isSuccessful)
            Output.Success(response.body()!!)
        else
        Output.Error(ErrorServerResponse(response.code(),response.message()))

    }
}