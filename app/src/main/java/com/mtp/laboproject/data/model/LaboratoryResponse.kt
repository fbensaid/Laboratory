package com.mtp.laboproject.data.model

data class LaboratoryResponse(
    val id: Int,
    val image: String,
    val is_new: Int,
    val language: String,
    val like_percent: Int,
    val rating: String,
    val title: String,
    val type: String,
    val vote_count: Int
)

data class LabsList(
    val labos: List<LaboratoryResponse>
)
