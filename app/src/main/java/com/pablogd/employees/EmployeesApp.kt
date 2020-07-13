package com.pablogd.employees

import android.app.Application
import com.pablogd.employees.di.injectFeatures
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EmployeesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (true) androidLogger()
            androidContext(this@EmployeesApp)
        }
        injectFeatures()
    }

}