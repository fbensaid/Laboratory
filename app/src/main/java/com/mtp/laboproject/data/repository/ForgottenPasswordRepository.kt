package com.mtp.laboproject.data.repository


import com.mtp.laboproject.data.model.ForgottenPasswordResponse
import com.mtp.laboproject.data.remoteApi.ApiInterface


class ForgottenPasswordRepository(private val api: ApiInterface) : BaseRepository() {

    //get latest news using safe api call
    suspend fun forgotPassword(email : String ) : ForgottenPasswordResponse?{
        return safeApiCall(
            //await the result of deferred type
            call = {api.forgotPassword(email).await()},
            error = "Error fetching news"
            //convert to mutable list
        )
    }


}