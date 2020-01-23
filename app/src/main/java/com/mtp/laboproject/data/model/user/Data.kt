package com.mtp.laboproject.data.model.user

data class Data(
    val TOKEN: String,
    val USERNAME: String?,
    val USR_ACTIVE: Boolean?,
    val USR_FIRST_NAME: String?,
    val USR_LAST_NAME: String?,
    val USR_MAIL: String?,
    val USR_ROLE: Int?,
    val USR_ROLE_DESCRIPTION: String?,
    val _id: String?
)