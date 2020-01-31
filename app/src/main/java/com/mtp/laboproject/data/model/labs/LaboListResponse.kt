package com.mtp.laboproject.data.model.labs

data class LaboListResponse(
    val data: List<LabsObjectResponse>,
    val success: Boolean
)