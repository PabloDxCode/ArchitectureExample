package com.pablogd.data.datasource.remote

import retrofit2.Response
import retrofit2.http.*

interface ConnectionApi {

    @GET
    suspend fun get(@HeaderMap headers: Map<String, String>, @Url url: String): Response<String>

    @POST
    suspend fun post(@HeaderMap headers: Map<String, String>, @Url url: String, @Body params: String): Response<String>

}