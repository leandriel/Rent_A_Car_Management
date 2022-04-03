package com.leandroid.system.rentacarmanagement.model

import com.leandroid.system.rentacarmanagement.ui.utils.CarStateAvailable
import com.leandroid.system.rentacarmanagement.ui.utils.ExpandableRecyclerViewAdapter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookingDetails(
    @Json(name = "_id")
    val id: String,
    val car: Car,
    val bookings: MutableList<Booking>
) : ExpandableRecyclerViewAdapter.ExpandableGroup<Any>() {
    constructor() : this(
        "",
        Car(),
        mutableListOf()
    )

    val nextBookingString: String
        get() = "Proxima reserva: 20/02/2022"

    override fun getExpandingItems(): MutableList<Any> {
        return bookings as MutableList<Any>
    }

    val carState: String
        get() {
            return CarStateAvailable.getState()
        }

    val carStateColor: Int
        get() {
            return CarStateAvailable.getColor()
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookingDetails

        if (id != other.id) return false
        if (car != other.car) return false
        if (bookings != other.bookings) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + car.hashCode()
        result = 31 * result + bookings.hashCode()
        return result
    }
}