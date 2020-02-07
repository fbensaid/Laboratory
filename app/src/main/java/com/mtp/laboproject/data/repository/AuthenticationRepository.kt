package com.mtp.laboproject.data.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.data.model.user.UserLoginResponse
import com.mtp.laboproject.data.model.user.UserLoginWithError
import com.mtp.laboproject.data.remoteApi.ApiInterface


class  AuthenticationRepository(private val api: ApiInterface)  : BaseRepository() {



     fun storeUser(user: UserLoginResponse) {
         sharedPreferences.apply {
             userResponse=user
             isStillConnected=true
         }
     }

     fun storeAuthData(mail: String, pass: String, isConnected: Boolean, finger: Boolean) {
        sharedPreferences.apply {
            email = mail
            password = pass
            isConnectedSuccessBefore = isConnected
            fingerPrint = finger
        }
     }

    suspend fun login(email :String, password: String): Any? {
        return safeApiCall(
            //await the result of deferred type
            call = { api.login(email,password,
                LaboApplication.appComponent.getPreferences().token!!).await() },
            error = "Error with getting User"
        )
    }


}