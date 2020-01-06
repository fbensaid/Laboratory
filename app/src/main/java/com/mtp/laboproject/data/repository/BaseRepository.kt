package com.mtp.laboproject.data.repository

import android.util.Log
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.data.remoteApi.Output
import com.mtp.laboproject.global.SharedPreferences

import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

open class BaseRepository {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

      constructor() {
        LaboApplication.appComponent.inject(baseRepository = this)
    }


    suspend fun <T : Any> safeApiCall(call : suspend()-> Response<T>, error : String) :  T?{
        val result = apiOutput(call, error)
        var output : T? = null
        when(result){
            is Output.Success ->
                output = result.output
            is Output.Error -> Log.e("Error", "The $error and the ${result.exception}")
        }
        return output

    }
    private suspend fun<T : Any> apiOutput(call: suspend()-> Response<T> , error: String) : Output<T>{
        val response = call.invoke()
        return if (response.isSuccessful)
            Output.Success(response.body()!!)
        else
            Output.Error(IOException("OOps .. Something went wrong due to  $error"))
    }
}