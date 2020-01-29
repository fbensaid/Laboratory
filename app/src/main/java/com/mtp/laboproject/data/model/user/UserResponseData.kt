package com.mtp.laboproject.data.model.user

data class UserResponseData(
    var TOKEN: String,
    var USERNAME: String?,
    var USR_ACTIVE: Boolean?,
    var USR_FIRST_NAME: String?,
    var USR_LAST_NAME: String?,
    var USR_MAIL: String?,
    var USR_ROLE: Int?,
    var USR_ROLE_DESCRIPTION: String?,
    var photoUrl:String?,
    var _id: String?
)