package com.pablogd.data.models.response

import com.pablogd.data.models.employee.EmployeeModel

data class EmployeesResponse(
        val data: List<EmployeeModel> = arrayListOf()
) : BaseEmployeeResponse()