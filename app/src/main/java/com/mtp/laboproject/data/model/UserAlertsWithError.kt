package com.mtp.laboproject.data.model

import com.mtp.laboproject.data.model.error.ErrorServerResponse
import com.mtp.laboproject.data.model.labs.LaboListResponse

data class UserAlertsWithError(
     var userLabsResponse: AlertsDetailsResponse?,
     var serverErrorResponse: ErrorServerResponse?
)


