package com.leandroid.system.rentacarmanagement.ui.utils

interface RecyclerListener {
    fun onClick(id: String)
    fun onMenuClickEdit(position: Int)
    fun onMenuClickDelete(position: Int)
}