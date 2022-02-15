package com.leandroid.system.rentacarmanagement.model

data class Car(
    val id: String,
    val patent: String,
    val model: String,
    val brand: Brand,
    val active: Boolean,
    val color: Color,
    val comment: String
){
    constructor(): this("", "", "", Brand(), false, Color(), "")
    val carDetails: String
      get() = "${brand.name} $model ${color.name} - $patent".uppercase()
}