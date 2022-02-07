package com.leandroid.system.rentacarmanagement.data

data class Car(
    val id: String,
    val number: String,
    val model: String,
    val brand: Brand,
    val active: Boolean
)