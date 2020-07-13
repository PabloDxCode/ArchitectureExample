package com.pablogd.data

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory
import com.google.gson.JsonParseException

class ParserValidatorAdapterFactory : TypeAdapterFactory {

    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
        val delegate = gson!!.getDelegateAdapter(this, type)

        if (delegate is ReflectiveTypeAdapterFactory.Adapter) {
            try {
                val f = delegate.javaClass.getDeclaredField("boundFields")
                f.isAccessible = true
                var boundFields = f.get(delegate) as Map<*, *>

                boundFields = object : LinkedHashMap<Any, Any>(boundFields) {
                    override fun get(key: Any): Any? = super.get(key) ?: throw JsonParseException("invalid property name: $key")
                }
                f.set(delegate, boundFields)

            } catch (e: Exception) {
                throw IllegalStateException(e)
            }
        }
        return delegate
    }

}