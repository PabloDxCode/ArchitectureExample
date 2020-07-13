package com.pablogd.data.datasource.local.daos

import androidx.room.*
import com.pablogd.data.datasource.local.entities.EmployeesEntity

@Dao
abstract class EmployeesDao {

    @Query("SELECT * FROM employee")
    abstract suspend fun getEmployees(): List<EmployeesEntity>

    @Query("SELECT * FROM employee WHERE id = :id")
    abstract suspend fun getEmployee(id: Long): EmployeesEntity

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(employee: EmployeesEntity): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(employees: List<EmployeesEntity>): Array<Long>

    @Query("DELETE FROM employee")
    abstract suspend fun clearData(): Int

}