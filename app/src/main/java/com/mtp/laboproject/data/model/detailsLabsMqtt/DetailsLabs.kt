package com.mtp.laboproject.data.model.detailsLabsMqtt

data class DetailsLabs(
    val clapets: List<Clapet>,
    val co2: Double,
    val date: String,
    val humidity: Double,
    val id: Int,
    val name: String,
    val porte: Boolean,
    val temperature: Double,
    val voc: Voc
)