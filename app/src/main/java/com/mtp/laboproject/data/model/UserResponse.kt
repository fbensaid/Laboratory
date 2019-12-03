package com.mtp.laboproject.data.model


data class UserResponse(
    val name: String,
    val email: String,
    val photoUrl: String,
    val emailVerified: String,
    val uid: String
)
