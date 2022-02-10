package com.leandroid.system.rentacarmanagement.model

data class Car(
    val id: String,
    val patent: String,
    val model: String,
    val brand: Brand,
    val active: Boolean,
    val color: String,
    val comment: String
){
    val carDetails: String
      get() = "${brand.name} $model $color - $patent".uppercase()
}