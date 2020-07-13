package com.pablogd.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pablogd.data.datasource.local.daos.EmployeesDao
import com.pablogd.data.datasource.local.entities.EmployeesEntity

@Database(entities = [
    EmployeesEntity::class
], version = 1, exportSchema = false)
abstract class EmployeesDataBase : RoomDatabase() {

    abstract fun employeesDao(): EmployeesDao

}