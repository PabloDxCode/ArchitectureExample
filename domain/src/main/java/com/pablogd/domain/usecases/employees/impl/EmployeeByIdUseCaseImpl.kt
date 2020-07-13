package com.pablogd.domain.usecases.employees.impl

import com.pablogd.data.models.request.EmployeeRequest
import com.pablogd.data.models.Failure
import com.pablogd.data.models.error.EmployeeError
import com.pablogd.data.models.response.EmployeeResponse
import com.pablogd.data.repositories.BaseRepository
import com.pablogd.domain.BuildConfig
import com.pablogd.domain.Employee
import com.pablogd.domain.toDomain
import com.pablogd.domain.usecases.BaseUseCaseImpl
import com.pablogd.domain.usecases.employees.EmployeeByIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeByIdUseCaseImpl(
        private val employeesRepositoryImpl: BaseRepository,
        private val scope: CoroutineDispatcher = Dispatchers.Main
) : BaseUseCaseImpl<Employee, String>(), EmployeeByIdUseCase {

    override fun success(success: suspend (Employee) -> Unit) = apply {
        this.mSuccess = success
    }

    override fun error(error: suspend (String) -> Unit) = apply {
        this.mError = error
    }

    override suspend fun getEmployee(id: String) {
        withContext(scope) {
            employeesRepositoryImpl
                    .setEndPoint(BuildConfig.URL_VERSION_1 + BuildConfig.END_POINT_EMPLOYEE_BY_ID)
                    .setBody(EmployeeRequest(id))
                    .get(EmployeeResponse::class.java, EmployeeError::class.java)
                    .either({ _, employeeResponse ->
                        mSuccess?.invoke(employeeResponse.toDomain())
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