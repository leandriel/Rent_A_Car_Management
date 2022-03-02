package com.leandroid.system.rentacarmanagement.model

data class ReturnCar(
    var id: String,
    var place: String,
    var dateTime: String
) {
    constructor() : this("", "", "")

    val placeDatetimeString: String
    get() = "Devolución: $place $dateTime"
}