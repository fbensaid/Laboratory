package com.mtp.laboproject.data.remoteApi

import com.mtp.laboproject.data.model.error.ErrorServerResponse

sealed class Output<out T : Any>{
    data class Success<out T : Any>(val output : T) : Output<T>()
    data class Error<out T : Any>(val exception: ErrorServerResponse)  : Output<T>()
}