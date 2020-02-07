package com.mtp.laboproject.data.model.labs

import com.mtp.laboproject.data.model.alert.AlertsDetailsResponse
import com.mtp.laboproject.data.model.error.ErrorServerResponse
import com.mtp.laboproject.data.model.labs.LaboListResponse

data class UserLabsWithError(
     var userLabsResponse: LaboListResponse?,
     var serverErrorResponse: ErrorServerResponse?
)


