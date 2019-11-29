package com.mtp.laboproject.data.rest

data class LaboratoryDetailsResponse(
    val __v: Int,
    val _id: String,
    val clapet: Int,
    val co2: Int,
    val nom: String,
    val porte: Int,
    val voc: Int
)