package com.pablogd.domain.usecases.employees.impl

import com.pablogd.data.datasource.local.daos.EmployeesDao
import com.pablogd.data.datasource.local.entities.EmployeesEntity
import com.pablogd.domain.Employee
import com.pablogd.domain.usecases.BaseUseCaseImpl
import com.pablogd.domain.usecases.employees.EmployeesDBUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeesDBUseCaseImpl(
        private val employeesDao: EmployeesDao,
        private val scope: CoroutineDispatcher = Dispatchers.Main
) : EmployeesDBUseCase {

    private var mSuccessEmployeesSaved: (suspend () -> Unit)? = null

    private var mSuccessGettingEmployees: (suspend (List<Employee>) -> Unit)? = null

    private var mError: (suspend (String) -> Unit)? = null

    override fun successEmployeesSaved(success: suspend () -> Unit) = apply {
        this.mSuccessEmployeesSaved = success
    }

    override fun successGettingEmployees(success: suspend (List<Employee>) -> Unit) = apply {
        this.mSuccessGettingEmployees = success
    }

    override fun error(error: suspend (String) -> Unit) = apply {
        this.mError = error
    }

    override suspend fun saveEmployees(employees: List<Employee>) {
        withContext(scope) {
            val entity = employees.map { EmployeesEntity(it.name, it.salary, it.age, it.photo) }
            employeesDao.clearData()
            val result = employeesDao.save(entity)
            if (result.isNotEmpty()) {
                mSuccessEmployeesSaved?.invoke()
            } else {
                mError?.invoke("")
            }
        }
    }

    override suspend fun getEmployees() {
        withContext(scope) {
            val result = employeesDao.getEmployees()
            val employees = result.map { Employee(it.getId().toString(), it.name, it.salary, it.age, it.photo) }
            mSuccessGettingEmployees?.invoke(employees)
        }
    }

}