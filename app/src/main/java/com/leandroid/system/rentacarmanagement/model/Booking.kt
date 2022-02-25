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

    val startDateString: String
    get() = "Desde: $startDate"

    val endDateString: String
        get() = "Hasta: $endDate"

    val drivingLicenseString: String
        get() = "Licencia: $drivingLicense"

    val flyString: String
        get() = "Vuelo: $fly"

    val hotelString: String
        get() = "Hotel: $hotel"

    val priceString: String
        get() = "Precio: $price"

    val commissionString: String
        get() = "Comisión: $commission"
}