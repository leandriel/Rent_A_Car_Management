package com.leandroid.system.rentacarmanagement.data.datasource

interface CarDataSource {
   fun getCars()
   fun getCar(id: String)
}