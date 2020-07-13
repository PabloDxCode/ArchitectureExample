package com.pablogd.domain.usecases.employees

import com.pablogd.domain.Employee

interface EmployeesDBUseCase {

    fun successEmployeesSaved(success: suspend () -> Unit): EmployeesDBUseCase

    fun successGettingEmployees(success: suspend (List<Employee>) -> Unit): EmployeesDBUseCase

    fun error(error: suspend (String) -> Unit): EmployeesDBUseCase

    suspend fun saveEmployees(employees: List<Employee>)

    suspend fun getEmployees()

}