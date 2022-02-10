package com.leandroid.system.rentacarmanagement.ui.utils

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val throwable: Throwable) : DataState<Nothing>()
    data class Loading(val loading: Boolean) : DataState<Nothing>()
    object Idle : DataState<Nothing>()
}