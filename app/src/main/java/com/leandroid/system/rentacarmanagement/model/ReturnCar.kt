package com.leandroid.system.rentacarmanagement.model

data class ReturnCar(
    val id: String,
    val place: String,
    val dateTime: String
) {
    constructor() : this("", "", "")

    val placeDatetimeString: String
    get() = "Devolución: $place $dateTime"
}