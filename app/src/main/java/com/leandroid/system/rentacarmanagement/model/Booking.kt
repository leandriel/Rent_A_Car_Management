package com.leandroid.system.rentacarmanagement.model

data class Booking(
    var id: String,
    var car: Car,
    var fly: String,
    var hotel: String,
    var drivingLicense: String,
    var startDate: String,
    var endDate: String,
    var returnCar: ReturnCar,
    var price: String,
    var commission: String,
    var comment: String
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
        "",
        ""
    )

    val isRequiredEmptyData: Boolean
        get() = car.id.isEmpty() || fly.isEmpty() || hotel.isEmpty() || drivingLicense.isEmpty() || startDate.isEmpty() || endDate.isEmpty()

    val startEndDate: String
        get() = "$startDate - $endDate"

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Booking

        if (id != other.id) return false
        if (car != other.car) return false
        if (fly != other.fly) return false
        if (hotel != other.hotel) return false
        if (drivingLicense != other.drivingLicense) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (returnCar != other.returnCar) return false
        if (price != other.price) return false
        if (commission != other.commission) return false
        if (comment != other.comment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + car.hashCode()
        result = 31 * result + fly.hashCode()
        result = 31 * result + hotel.hashCode()
        result = 31 * result + drivingLicense.hashCode()
        result = 31 * result + startDate.hashCode()
        result = 31 * result + endDate.hashCode()
        result = 31 * result + returnCar.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + commission.hashCode()
        result = 31 * result + comment.hashCode()
        return result
    }


}