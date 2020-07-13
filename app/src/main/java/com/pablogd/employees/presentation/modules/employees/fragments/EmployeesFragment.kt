package com.pablogd.employees.presentation.modules.employees.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.pablogd.employees.databinding.FragmentEmployeesBinding
import com.pablogd.employees.presentation.common.BaseFragment
import com.pablogd.employees.presentation.modules.employees.adapters.EmployeesAdapter
import com.pablogd.employees.presentation.modules.employees.viewmodel.EmployeesViewModel
import org.koin.android.ext.android.inject

class EmployeesFragment: BaseFragment() {

    private var binding: FragmentEmployeesBinding? = null

    private val employeesViewModel: EmployeesViewModel by inject()

    private var adapter: EmployeesAdapter?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEmployeesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = EmployeesAdapter{ employeesViewModel.itemClicked(it) }
        binding?.rvEmployees?.adapter = adapter
        observers()
        employeesViewModel.viewLoaded()
    }

    private fun observers(){
        employeesViewModel.uiStateLiveData.observe(viewLifecycleOwner, Observer { uiStateNullable ->
            uiStateNullable?.let { uiState ->
                viewListener?.showHideProgress(uiState.showProgress)
                uiState.error?.let {
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                }
            }
        })
        employeesViewModel.employeeList.observe(viewLifecycleOwner, Observer{ employeeList ->
            employeeList?.let {
                adapter?.submitList(it)
            }
        })
        employeesViewModel.employee.observe(viewLifecycleOwner, Observer { employee ->
            employee?.let {
                Toast.makeText(activity, it.name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        employeesViewModel.onDestroy()
    }

}