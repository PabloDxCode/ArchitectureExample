package com.pablogd.domain

import com.pablogd.data.models.response.EmployeeResponse
import com.pablogd.data.models.response.EmployeesResponse

data class Employee(
        var id: String = "",
        var name: String = "",
        var salary: String = "",
        var age: String = "",
        var photo: String = ""
)

fun EmployeesResponse.toDomain() = data.map { Employee(it.id, it.employeeName, it.employeeSalary, it.employeeAge, it.profileImage) }

fun EmployeeResponse.toDomain() = Employee(data.id, data.employeeName, data.employeeSalary, data.employeeAge, data.profileImage)