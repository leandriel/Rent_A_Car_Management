package com.leandroid.system.rentacarmanagement.model

data class Booking(
    val id: String,
    val car: Car,
    val fly: String,
    val hotel: String,
    val drivingLicense: String,
    val startDate: String,
    val endDate: String,
    val returnCar: ReturnCar,
    val price: String,
    val commission: String
) {
    constructor() : this(
        "",
        Car(),
        "",
        "",
        "",
        "",
        "", ReturnCar(),
        "",
        ""
    )
}