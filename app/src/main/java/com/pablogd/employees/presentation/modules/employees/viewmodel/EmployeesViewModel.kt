package com.pablogd.employees.presentation.modules.employees.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pablogd.domain.Employee
import com.pablogd.domain.usecases.employees.EmployeeByIdUseCase
import com.pablogd.domain.usecases.employees.factories.EmployeesFactory
import com.pablogd.employees.presentation.common.BaseViewModel
import com.pablogd.employees.presentation.models.UIStateModel
import kotlinx.coroutines.launch

class EmployeesViewModel(
    private val employeesFactory: EmployeesFactory,
    private val employeeByIdUseCase: EmployeeByIdUseCase
) : BaseViewModel() {

    private val _employeeList = MutableLiveData<List<Employee>>()
    val employeeList: LiveData<List<Employee>>
        get() = _employeeList

    private val _employee = MutableLiveData<Employee>()
    val employee: LiveData<Employee>
        get() = _employee

    fun viewLoaded() {
        uiScope.launch {
            _uiStateLiveData.value = UIStateModel(true)
            employeesFactory
                .success {
                    _uiStateLiveData.value = UIStateModel(false)
                    _employeeList.value = it
                }
                .error {
                    _uiStateLiveData.value = UIStateModel(false)
                }
                .getEmployees()
        }
    }

    fun itemClicked(id: String){
        uiScope.launch {
            _uiStateLiveData.value = UIStateModel(true)
            employeeByIdUseCase
                    .success {
                        _uiStateLiveData.value = UIStateModel(false)
                        _employee.value = it
                    }
                    .error {
                        _uiStateLiveData.value = UIStateModel(false, it)
                    }
                    .getEmployee(id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _employeeList.value = null
        _employee.value = null
    }

}