package com.pablogd.data.models

import okhttp3.Headers

sealed class Either<out S, out E, out F> {

    data class Success<out S>(val headers: Headers, val s: S) : Either<S, Nothing, Nothing>()
    data class Error<out E>(val headers: Headers, val e: E) : Either<Nothing, E, Nothing>()
    data class ServerError<out F>(val e: F) : Either<Nothing, Nothing, F>()

    suspend fun either(funS: suspend (Headers, S) -> Unit, funE: suspend (Headers, E) -> Unit, funSE: suspend (F) -> Unit): Any = when (this) {
        is Success -> funS(headers, s)
        is Error -> funE(headers, e)
        is ServerError -> funSE(e)
    }

}
