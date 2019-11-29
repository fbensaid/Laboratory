package com.mtp.laboproject.data.remoteApi

import com.mtp.laboproject.data.global.Constants
import com.mtp.laboproject.data.repository.LaboratoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

abstract class SafeLaboratoryRequestApi {


    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else
            throw ApiException(response.errorBody().toString())
    }


}

class ApiException(message: String) : IOException(message)