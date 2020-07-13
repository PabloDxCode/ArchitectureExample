package com.pablogd.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeesEntity(
        val name: String,
        val salary: String,
        var age: String,
        var photo: String
) {

    @field:PrimaryKey(autoGenerate = true)
    private var id: Long = 0

    fun getId() = id

    fun setId(id: Long) {
        this.id = id
    }

}