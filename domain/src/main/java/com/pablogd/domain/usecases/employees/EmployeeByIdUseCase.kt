package com.pablogd.domain.usecases.employees

import com.pablogd.domain.Employee

interface EmployeeByIdUseCase {

    fun success(success: suspend (Employee) -> Unit): EmployeeByIdUseCase

    fun error(error: suspend (String) -> Unit): EmployeeByIdUseCase

    suspend fun getEmployee(id: String)

}