package com.pablogd.employees.di

import com.pablogd.data.di.databaseModule
import com.pablogd.data.di.networkModule
import com.pablogd.data.di.repositoriesModule
import com.pablogd.domain.di.factoriesModule
import com.pablogd.domain.di.useCasesModule
import com.pablogd.employees.presentation.modules.employees.viewmodel.EmployeesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectFeatures() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
            arrayListOf(
                    viewModelModule,
                    useCasesModule,
                    factoriesModule,
                    repositoriesModule,
                    networkModule,
                    databaseModule
            )
    )
}

val viewModelModule: Module = module {
    viewModel {
        EmployeesViewModel(employeesFactory = get(), employeeByIdUseCase = get())
    }
}