package com.leandroid.system.rentacarmanagement.model

data class Booking(
    val id: String,
    val car: Car,
    val fly: String,
    val startDate: String,
    val endDate: String
)