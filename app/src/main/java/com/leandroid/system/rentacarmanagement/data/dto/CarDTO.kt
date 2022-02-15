package com.leandroid.system.rentacarmanagement.data.dto

import com.leandroid.system.rentacarmanagement.model.Brand
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.model.Color

data class CarDTO(
    val car: Car,
    val brands: MutableList<Brand>,
    val colors: MutableList<Color>
) {
    constructor() : this(
        Car(),
        mutableListOf(),
        mutableListOf()
    )
}