package com.pablogd.employees.presentation.modules.employees.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pablogd.employees.R
import com.pablogd.employees.databinding.ActivityMainBinding
import com.pablogd.employees.presentation.common.ViewListener

class MainActivity : AppCompatActivity(), ViewListener {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }

    override fun showHideProgress(isShowing: Boolean) {
        binding?.let { it.pbEmployees.visibility = if (isShowing) View.VISIBLE else View.GONE }
    }

}