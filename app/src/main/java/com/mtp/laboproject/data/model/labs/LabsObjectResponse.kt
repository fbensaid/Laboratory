package com.mtp.laboproject.data.model.labs

data class LabsObjectResponse(
    val _id: String,
    val name: String,
    val number_lab: Int,
    val sensors: List<Sensor>
)