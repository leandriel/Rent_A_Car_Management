package com.leandroid.system.rentacarmanagement.data.dto

import com.leandroid.system.rentacarmanagement.model.Brand
import com.leandroid.system.rentacarmanagement.model.Car

data class CarDTO(
    val car: Car,
    val brands: MutableList<Brand>
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
            ""
        ),
        mutableListOf()
    )
}