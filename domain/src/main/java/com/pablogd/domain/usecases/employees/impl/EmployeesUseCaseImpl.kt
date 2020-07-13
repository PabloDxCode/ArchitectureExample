package com.pablogd.domain.usecases.employees.impl

import com.pablogd.data.models.Failure
import com.pablogd.data.models.error.EmployeeError
import com.pablogd.data.models.response.EmployeesResponse
import com.pablogd.data.repositories.BaseRepository
import com.pablogd.domain.BuildConfig
import com.pablogd.domain.Employee
import com.pablogd.domain.toDomain
import com.pablogd.domain.usecases.BaseUseCaseImpl
import com.pablogd.domain.usecases.employees.EmployeesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeesUseCaseImpl(
        private val employeesRepositoryImpl: BaseRepository,
        private val scope: CoroutineDispatcher = Dispatchers.Main
) : BaseUseCaseImpl<List<Employee>, String>(), EmployeesUseCase {

    override suspend fun success(success: suspend (List<Employee>) -> Unit) = apply {
        this.mSuccess = success
    }

    override suspend fun error(error: suspend (String) -> Unit) = apply {
        this.mError = error
    }

    override suspend fun getEmployees() {
        withContext(scope) {
            employeesRepositoryImpl
                    .setEndPoint(BuildConfig.URL_VERSION_1 + BuildConfig.END_POINT_EMPLOYEES)
                    .get(EmployeesResponse::class.java, EmployeeError::class.java)
                    .either({ _, employeesResponse ->
                        mSuccess?.invoke(employeesResponse.toDomain())
                    }, { _, employeeError ->
                        mError?.invoke(employeeError.data ?: "")
                    }, {
                        when (it) {
                            is Failure.GenericError -> mError?.invoke(it.exception.message ?: "")
                            is Failure.ServerError -> mError?.invoke(it.responseBody)
                        }
                    })
        }
    }

}