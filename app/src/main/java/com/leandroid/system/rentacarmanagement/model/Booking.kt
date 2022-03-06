package com.leandroid.system.rentacarmanagement.model

import com.leandroid.system.rentacarmanagement.ui.utils.DateTimeUtils.dateShortFormatString

data class Booking(
    var id: String,
    var car: Car,
    var fly: String,
    var hotel: String,
    var drivingLicense: String,
    var startDate: Long?,
    var deliveryPlace: String,
    var deliveryTime: String = "00:00",
    var endDate: Long?,
    var returnPlace: String,
    var returnTime: String = "00:00",
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
        null,
        "",
        "",
        null,
        "",
        "",
        "",
        "",
        ""
    )

    val isRequiredEmptyData: Boolean
        get() = car.id.isEmpty() || fly.isEmpty() || hotel.isEmpty() || drivingLicense.isEmpty() || startDate == null || endDate == null

    val startEndDate: String
        get() {
            return if (startDate != null && endDate != null) {
                "${dateShortFormatString(startDate!!)} - ${dateShortFormatString(endDate!!)}"
            } else {
                ""
            }
        }

    val startDateTime: String
        get() {
            return if (startDate != null) {
                "${dateShortFormatString(startDate!!)} - $deliveryTimeHs"
            } else {
                ""
            }
        }
    
    val deliveryTimeHs: String
        get() = "$deliveryTime$HS"


    val endDateTime: String
        get() {
            return if (endDate != null) {
                "${dateShortFormatString(endDate!!)} - $returnTimeHs"
            } else {
                ""
            }
        }

    val returnTimeHs: String
        get() = "$returnTime$HS"

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
        if (deliveryPlace != other.deliveryPlace) return false
        if (deliveryTime != other.deliveryTime) return false
        if (endDate != other.endDate) return false
        if (returnPlace != other.returnPlace) return false
        if (returnTime != other.returnTime) return false
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
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + deliveryPlace.hashCode()
        result = 31 * result + deliveryTime.hashCode()
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + returnPlace.hashCode()
        result = 31 * result + returnTime.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + commission.hashCode()
        result = 31 * result + comment.hashCode()
        return result
    }

    companion object {
        private const val HS = "hs"
    }
}