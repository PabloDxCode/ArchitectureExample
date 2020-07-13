package com.pablogd.domain.usecases.employees

import com.pablogd.domain.Employee

interface EmployeesUseCase {

    suspend fun success(success: suspend (List<Employee>) -> Unit): EmployeesUseCase

    suspend fun error(error: suspend (String) -> Unit): EmployeesUseCase

    suspend fun getEmployees()

}