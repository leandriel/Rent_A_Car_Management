package com.leandroid.system.rentacarmanagement.ui.booking

interface BookingListener {
    fun onClick(id: String)
    fun onMenuClickEdit(position: Int)
    fun onMenuClickDelete(position: Int)
}