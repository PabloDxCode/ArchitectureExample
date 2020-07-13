package com.pablogd.domain.di

import com.pablogd.domain.usecases.employees.EmployeeByIdUseCase
import com.pablogd.domain.usecases.employees.EmployeesDBUseCase
import com.pablogd.domain.usecases.employees.EmployeesUseCase
import com.pablogd.domain.usecases.employees.factories.EmployeesFactory
import com.pablogd.domain.usecases.employees.factories.impl.EmployeesFactoryImpl
import com.pablogd.domain.usecases.employees.impl.EmployeeByIdUseCaseImpl
import com.pablogd.domain.usecases.employees.impl.EmployeesDBUseCaseImpl
import com.pablogd.domain.usecases.employees.impl.EmployeesUseCaseImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCasesModule: Module = module {
    factory { EmployeesUseCaseImpl(get(named("employees"))) as EmployeesUseCase }
    factory { EmployeeByIdUseCaseImpl(get(named("employees"))) as EmployeeByIdUseCase }
    factory { EmployeesDBUseCaseImpl(employeesDao = get()) as EmployeesDBUseCase }
}

val factoriesModule: Module = module {
    factory { EmployeesFactoryImpl(employeesUseCase = get(), employeesDBUseCase = get()) as EmployeesFactory }
}