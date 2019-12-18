package com.mtp.laboproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USERID=0
@Entity
data class UserResponse(
    val name: String?,
    val email: String?,
    val photoUrl: String?,
    var uid: String?

){
    // we need to store only one user
    @PrimaryKey(autoGenerate= false)
    var id_db_user: Int= CURRENT_USERID
}

