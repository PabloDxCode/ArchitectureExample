package com.pablogd.data.models.employee

import com.google.gson.annotations.SerializedName

data class EmployeeModel(
    var id: String = "",
    @SerializedName("employee_name") var employeeName: String = "",
    @SerializedName("employee_salary") var employeeSalary: String = "",
    @SerializedName("employee_age") var employeeAge: String = "",
    @SerializedName("profile_image") var profileImage: String = ""
)