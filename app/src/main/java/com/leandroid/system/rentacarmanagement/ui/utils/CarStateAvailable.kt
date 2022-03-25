package com.leandroid.system.rentacarmanagement.ui.utils

import com.leandroid.system.rentacarmanagement.R

object CarStateAvailable: CarState {
    override fun getState(): String {
        return CarState.AVAILABLE
    }

    override fun getColor(): Int {
        return R.color.teal_700
    }
}