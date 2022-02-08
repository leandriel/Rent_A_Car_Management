package com.leandroid.system.rentacarmanagement.model

data class Reserve(
    val id: String,
    val car: Car,
    val fly: String,
    val startDate: String,
    val endDate: String
)