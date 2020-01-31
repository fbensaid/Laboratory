package com.mtp.laboproject.data.model.labs

data class SenserId(
    val _id: String,
    val data: List<LaboratoryDataResponse2>,
    val name: String,
    val type: Int
)