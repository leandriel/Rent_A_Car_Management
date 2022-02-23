package com.leandroid.system.rentacarmanagement.model

import com.leandroid.system.rentacarmanagement.ui.utils.ExpandableRecyclerViewAdapter

data class BookingDetails(
    val id: String,
    val car: Car,
    val bookings: MutableList<Booking>
): ExpandableRecyclerViewAdapter.ExpandableGroup<Any>(){
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