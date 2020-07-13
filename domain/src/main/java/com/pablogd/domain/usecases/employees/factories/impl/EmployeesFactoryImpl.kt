package com.pablogd.domain.usecases.employees.factories.impl

import com.pablogd.domain.Employee
import com.pablogd.domain.usecases.employees.EmployeesDBUseCase
import com.pablogd.domain.usecases.employees.EmployeesUseCase
import com.pablogd.domain.usecases.employees.factories.EmployeesFactory

class EmployeesFactoryImpl(
        private val employeesUseCase: EmployeesUseCase,
        private val employeesDBUseCase: EmployeesDBUseCase
) : EmployeesFactory {

    private var mSuccess: (suspend (List<Employee>) -> Unit)? = null

    private var mError: (suspend () -> Unit)? = null

    override suspend fun success(success: suspend (List<Employee>) -> Unit) = apply {
        this.mSuccess = success
    }

    override suspend fun error(error: suspend () -> Unit) = apply {
        this.mError = error
    }

    override suspend fun getEmployees() {
        employeesUseCase
                .success {
                    this.saveEmployees(it)
                }
                .error { mError?.invoke() }
                .getEmployees()
    }

    override suspend fun saveEmployees(employees: List<Employee>) {
        employeesDBUseCase
                .successEmployeesSaved { getEmployeesFromDB() }
                .error { mError?.invoke() }
                .saveEmployees(employees)
    }

    override suspend fun getEmployeesFromDB() {
        employeesDBUseCase
                .successGettingEmployees { mSuccess?.invoke(it) }
                .getEmployees()
    }

}