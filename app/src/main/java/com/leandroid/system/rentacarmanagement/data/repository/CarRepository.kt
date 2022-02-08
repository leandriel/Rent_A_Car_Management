package com.leandroid.system.rentacarmanagement.data.repository

interface CarRepository {
    fun getCars()
    fun getCar(id: String)
}