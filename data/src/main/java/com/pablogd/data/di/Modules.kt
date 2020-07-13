package com.pablogd.data.di

import com.pablogd.data.BuildConfig
import com.pablogd.data.datasource.local.createDB
import com.pablogd.data.datasource.local.daos.EmployeesDao
import com.pablogd.data.datasource.local.getEmployeesDao
import com.pablogd.data.datasource.remote.ConnectionApi
import com.pablogd.data.repositories.BaseRepository
import com.pablogd.data.repositories.impl.EmployeesRepositoryImpl
import com.pablogd.network.createNetworkClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.net.CookieManager

val repositoriesModule: Module = module {
    factory(named("employees")) { EmployeesRepositoryImpl(api = get(), url = BuildConfig.EMPLOYEES_URL) as BaseRepository }
}

val networkModule: Module = module {
    single { get<Retrofit>().create(ConnectionApi::class.java) }
    single { createNetworkClient(CookieManager()) }
}

val databaseModule: Module = module {
    single { createDB(context = get()) }
    factory { getEmployeesDao(db = get()) }
}