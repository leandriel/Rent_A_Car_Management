package com.leandroid.system.rentacarmanagement.ui.utils

interface CarState {
    fun getState(): String
    fun getColor(): Int

    companion object {
        const val AVAILABLE = "DISPONIBLE"
        const val RESERVED = "RESERVADO"
        const val RESERVED_TODAY = "RESERVADO HOY"
    }
}