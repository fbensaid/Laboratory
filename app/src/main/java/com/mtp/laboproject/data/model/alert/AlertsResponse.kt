package com.mtp.laboproject.data.model.alert

import com.mtp.laboproject.data.model.error.ErrorApiResponse

data class AlertsResponse(
    val data: List<AlertsDetailsResponse>?,
    val success: Boolean?,
    var error: ErrorApiResponse?
)