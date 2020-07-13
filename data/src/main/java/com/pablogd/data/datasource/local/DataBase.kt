package com.pablogd.data.datasource.local

import android.content.Context
import androidx.room.Room
import com.pablogd.data.BuildConfig
import com.pablogd.data.datasource.local.daos.EmployeesDao

fun createDB(context: Context): EmployeesDataBase {
    return Room.databaseBuilder(context, EmployeesDataBase::class.java, BuildConfig.DATABASE_NAME)
            .build()
}

fun getEmployeesDao(db: EmployeesDataBase): EmployeesDao = db.employeesDao()