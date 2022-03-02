package com.leandroid.system.rentacarmanagement.data.dto

import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.Car

data class BookingDTO(
    val booking: Booking,
    val cars: MutableList<Car>
) {
    constructor() : this(
        Booking(),
        mutableListOf()
    )
}