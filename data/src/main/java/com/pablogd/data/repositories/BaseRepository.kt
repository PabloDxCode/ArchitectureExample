package com.pablogd.data.repositories

import com.pablogd.data.models.Either
import com.pablogd.data.models.Failure
import com.pablogd.data.repositories.impl.BaseRepositoryImpl

interface BaseRepository {

    fun setUrl(url: String): BaseRepository

    fun setEndPoint(endPoint: String): BaseRepository

    fun setHeaders(headers: Map<String, String>, headerType: BaseRepositoryImpl.HeaderType): BaseRepository

    fun setBody(body: Any): BaseRepository

    fun setPostParams(params: Map<String, String>): BaseRepository

    suspend fun<S, E> get(successType: Class<S>, errorType: Class<E>): Either<S, E, Failure>

    suspend fun<S, E> post(successType: Class<S>, errorType: Class<E>): Either<S, E, Failure>

}