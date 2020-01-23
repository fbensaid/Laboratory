package com.mtp.laboproject.data.model.labs

data class LabsListResponse(
    val `data`: List<LaboratoryResponse>,
    val success: Boolean
)