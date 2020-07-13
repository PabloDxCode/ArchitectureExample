package com.pablogd.domain.usecases

abstract class BaseUseCaseImpl<T, E> {

    protected var mSuccess: (suspend (T) -> Unit)? = null

    protected var mError: (suspend (E) -> Unit)? = null

}