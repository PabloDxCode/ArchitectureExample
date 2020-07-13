package com.pablogd.data.models.error

import com.pablogd.data.models.response.BaseEmployeeResponse

data class EmployeeError(
    val data: String? = null
): BaseEmployeeResponse()