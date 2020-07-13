package com.pablogd.data.models.response

import com.pablogd.data.models.employee.EmployeeModel

data class EmployeeResponse(
    var data: EmployeeModel
): BaseEmployeeResponse()