package com.mtp.laboproject.data.model.user

import com.mtp.laboproject.data.model.error.ErrorServerResponse
import com.mtp.laboproject.data.model.labs.LaboListResponse

data class UserLabsWithError(
     var userLabsResponse: LaboListResponse?,
     var serverErrorResponse: ErrorServerResponse?
)


