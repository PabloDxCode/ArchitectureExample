package com.pablogd.data.repositories.impl

import com.pablogd.data.datasource.remote.ConnectionApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeesRepositoryImpl(
        private val api: ConnectionApi, url: String, private val scope: CoroutineDispatcher = Dispatchers.IO
) : BaseRepositoryImpl(url) {

    override suspend fun <S, E> get(successType: Class<S>, errorType: Class<E>) = withContext(scope) {
        apiCall({ api.get(mHeaders, getUrl()) }, successType, errorType)
    }

    override suspend fun <S, E> post(successType: Class<S>, errorType: Class<E>) = withContext(scope) {
        apiCall({ api.post(mHeaders, postUrl(), getBody()) }, successType, errorType)
    }

}