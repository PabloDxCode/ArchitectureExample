package com.pablogd.data.models.response

abstract class BaseEmployeeResponse {

    private var status: String = ""

    fun getStatus() = status

    fun setStatus(status: String){
        this.status = status
    }

}