package com.mtp.laboproject.data.model.labs

data class LaboratoryDataResponse2(
    val date: String,
    val temperature: String,
    val humidite: String,
    val co2: String,
    val _id: String,
    val open: Boolean
)
