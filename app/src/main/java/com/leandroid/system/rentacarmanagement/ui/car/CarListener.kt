package com.leandroid.system.rentacarmanagement.ui.car

interface CarListener {
    fun onClick(id: String)
    fun onMenuClickEdit(position: Int)
    fun onMenuClickDelete(position: Int)
}