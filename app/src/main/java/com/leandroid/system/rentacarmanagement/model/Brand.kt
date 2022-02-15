package com.leandroid.system.rentacarmanagement.model

data class Brand(
    val id: String,
    val name: String
){
    constructor(): this("", "")

    override fun toString(): String {
        return name
    }
}