package com.pablogd.data.utils

import java.lang.StringBuilder

object Utils {

    const val HEADER_TITLE_CONTENT_TYPE = "Content-Type"
    const val HEADER_TITLE_ACCEPT = "Accept"
    const val HEADER_TITLE_AUTHORIZATION = "Authorization"
    const val HEADER_VALUE_JSON = "application/json"
    const val HEADER_VALUE_FORM_URLENCODED = "application/x-www-form-urlencoded"
    const val HEADER_VALUE_PDF = "application/pdf"

    fun configJsonHeaders(headers: HashMap<String, String>): HashMap<String, String> {
        headers[HEADER_TITLE_CONTENT_TYPE] = HEADER_VALUE_JSON
        return headers
    }

    fun setParams(url: String, params: Map<String, String>): String {
        if (params.isEmpty()) return url

        var urlParams = "$url?"
        val concatParams = StringBuilder("?")
        for (item in params.entries) {
            if (urlParams.contains("{${item.key}}")) {
                urlParams = urlParams.replace("{${item.key}}", item.value)
            } else {
                concatParams.append(item.key).append("=").append(item.value)
            }
        }
        if(concatParams.length == 1) concatParams.deleteCharAt(concatParams.length - 1)
        return urlParams + concatParams
    }

}