package com.pablogd.employees.presentation.modules.employees.adapters

import androidx.recyclerview.widget.DiffUtil
import com.pablogd.domain.Employee

class EmployeesDiffUtil : DiffUtil.ItemCallback<Employee>() {

    override fun areItemsTheSame(oldItem: Employee, newItem: Employee) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee) = oldItem == newItem

}