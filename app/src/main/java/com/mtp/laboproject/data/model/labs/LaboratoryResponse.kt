package com.mtp.laboproject.data.model.labs


data class LaboratoryResponse(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val name: String,
    val notifications: List<Any>,
    val number_lab: Int,
    val sensors: List<Sensor>,
    val updatedAt: String,
    val users: List<Any>
)