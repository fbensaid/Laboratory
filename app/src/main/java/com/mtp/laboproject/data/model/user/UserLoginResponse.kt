package com.mtp.laboproject.data.model.user

import com.mtp.laboproject.data.model.error.ErrorApiResponse

data class UserLoginResponse(
    var data: UserResponseData,
    var success: Boolean,
    var error:ErrorApiResponse?

)