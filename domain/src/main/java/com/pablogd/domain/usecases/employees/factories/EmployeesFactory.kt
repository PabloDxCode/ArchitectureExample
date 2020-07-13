package com.pablogd.domain.usecases.employees.factories

import com.pablogd.domain.Employee

interface EmployeesFactory {

    suspend fun success(success: suspend (List<Employee>) -> Unit): EmployeesFactory

    suspend fun error(error: suspend () -> Unit): EmployeesFactory

    suspend fun getEmployees()

    suspend fun saveEmployees(employees: List<Employee>)

    suspend fun getEmployeesFromDB()

}