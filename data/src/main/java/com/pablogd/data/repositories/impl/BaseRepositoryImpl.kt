package com.pablogd.data.repositories.impl

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.pablogd.data.ParserValidatorAdapterFactory
import com.pablogd.data.repositories.BaseRepository
import com.pablogd.data.utils.Utils
import com.pablogd.data.models.Either
import com.pablogd.data.models.Failure
import retrofit2.Response
import java.lang.Exception

abstract class BaseRepositoryImpl(url: String) : BaseRepository {

    private var mUrl: String = url

    private var mEndPoint: String = ""

    protected var mHeaders: Map<String, String> = HashMap()

    private var mBody: Any? = null

    private var mPostParams: Map<String, String> = HashMap()

    final override fun setUrl(url: String) = apply {
        this.mUrl = url
    }

    override fun setEndPoint(endPoint: String) = apply {
        this.mEndPoint = endPoint
    }

    final override fun setHeaders(headers: Map<String, String>, headerType: HeaderType) = apply {
        this.mHeaders = when (headerType) {
            HeaderType.JSON -> Utils.configJsonHeaders(HashMap(headers))
            HeaderType.NONE -> headers
        }
    }

    final override fun setBody(body: Any) = apply {
        this.mBody = body
    }

    override fun setPostParams(params: Map<String, String>) = apply {
        this.mPostParams = params
    }

    protected fun getUrl(): String = Utils.setParams(mUrl + mEndPoint, mBody?.serializeToMap() ?: HashMap())

    protected fun postUrl(): String = Utils.setParams(mUrl + mEndPoint, mPostParams)

    protected fun getBody() = Gson().toJson(mBody ?: "")

    protected suspend fun <S, E> apiCall(call: suspend () -> Response<String>, successType: Class<S>,
                                         errorType: Class<E>): Either<S, E, Failure> = try {
        val response = call.invoke()
        if (response.isSuccessful) {
            if (successType === String::class.java) {
                Either.Success(response.headers(), response.body().toString() as S)
            } else {
                processResponse(response, successType, errorType)
            }
        } else {
            Either.ServerError(Failure.ServerError(response.code(), response.errorBody()?.toString()
                    ?: response.message(), response.headers()))
        }
    } catch (e: Exception) {
        Either.ServerError(Failure.ServerError(-1, "", null))
    }

    @Throws(Exception::class)
    private fun <S, E> processResponse(response: Response<String>, successType: Class<S>,
                                       errorType: Class<E>): Either<S, E, Failure> {
        val successResponse = parse(response.body().toString(), successType)
        return if (successResponse == null) {
            if (errorType === String::class.java) {
                Either.Error(response.headers(), response.body().toString() as E)
            } else {
                val errorResponse = parse(response.body().toString(), errorType) ?: throw Exception()
                Either.Error(response.headers(), errorResponse)
            }
        } else {
            Either.Success(response.headers(), successResponse)
        }
    }

    @Throws(Exception::class)
    private fun <T> parse(response: String, classType: Class<T>): T? = try {
        val gson = GsonBuilder().serializeNulls().registerTypeAdapterFactory(ParserValidatorAdapterFactory()).create()
        gson.fromJson(response, classType)
    } catch (e: Exception) {
        null
    }

    enum class HeaderType {

        JSON,
        NONE

    }

}

/**
 * Method to convert a data class to a map
 */
fun <T> T.serializeToMap(): Map<String, String> {
    return convert()
}

/**
 * Method to convert an object of type I to type O
 */
inline fun <I, reified O> I.convert(): O {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}