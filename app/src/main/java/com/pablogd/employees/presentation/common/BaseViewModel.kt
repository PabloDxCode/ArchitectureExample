package com.pablogd.employees.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pablogd.employees.presentation.models.UIStateModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

private interface Destroy {
    fun onDestroy()
}

abstract class BaseViewModel: ViewModel(), Destroy {

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    protected var _uiStateLiveData = MutableLiveData<UIStateModel>()

    val uiStateLiveData: LiveData<UIStateModel>
        get() = _uiStateLiveData

    override fun onDestroy() {
        _uiStateLiveData.value = null
        viewModelJob.cancel()
    }

}