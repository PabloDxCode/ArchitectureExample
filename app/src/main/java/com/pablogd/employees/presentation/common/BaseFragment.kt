package com.pablogd.employees.presentation.common

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected var viewListener: ViewListener?= null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ViewListener) {
            viewListener = context
        }
    }

    /**
     * onDetach
     */
    override fun onDetach() {
        super.onDetach()
        viewListener = null
    }

}