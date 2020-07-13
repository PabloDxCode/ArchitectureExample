package com.pablogd.employees.presentation.modules.employees.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pablogd.domain.Employee
import com.pablogd.employees.R
import com.pablogd.employees.databinding.ItemEmployeeBinding

class EmployeesAdapter(
        private val listener: (id: String) -> Unit
): ListAdapter<Employee, EmployeesAdapter.EmployeeHolder>(EmployeesDiffUtil()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_employee, parent, false)
        return EmployeeHolder(view, listener)
    }

    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EmployeeHolder(view: View, private val listener: (id: String) -> Unit): RecyclerView.ViewHolder(view) {

        private val binding = ItemEmployeeBinding.bind(view)

        fun bind(employee: Employee) = with(binding){
            tvName.text = employee.name
            tvSalary.text = employee.salary
            binding.root.setOnClickListener { listener.invoke(employee.id) }
        }

    }

}