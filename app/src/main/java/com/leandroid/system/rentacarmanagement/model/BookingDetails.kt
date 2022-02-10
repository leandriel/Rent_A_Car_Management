package com.leandroid.system.rentacarmanagement.model

data class BookingDetails(
    val car: Car,
    val bookings: MutableList<Booking>
) {
    constructor() : this(
        Car(
            "",
            "",
            "",
            Brand(
                "",
                ""
            ),
            false,
            "",
        ""),
        mutableListOf()
    )
}