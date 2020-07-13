package com.pablogd.data.models

import okhttp3.Headers

sealed class Failure {
    class GenericError(val exception: Exception = Exception()) : Failure()
    class ServerError(val code: Int, val responseBody: String, headers: Headers?) : Failure()
    object NetworkConnection : Failure()
}
