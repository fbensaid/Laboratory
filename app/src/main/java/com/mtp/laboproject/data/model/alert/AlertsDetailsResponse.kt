package com.mtp.laboproject.data.model.alert

data class AlertsDetailsResponse(
    val __v: Int,
    val _id: String,
    val content: String,
    val createdAt: String,
    val ref_lab: String,
    val title: String,
    val type: Int,
    val updatedAt: String
)