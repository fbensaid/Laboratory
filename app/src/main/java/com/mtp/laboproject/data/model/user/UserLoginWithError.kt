package com.mtp.laboproject.data.model.user

import com.mtp.laboproject.data.model.error.ErrorResponse

data class UserLoginWithError(
     var userLoginResponse: UserLoginResponse?,
     var serverErrorResponse: ErrorResponse?
)


