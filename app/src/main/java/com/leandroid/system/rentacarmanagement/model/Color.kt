package com.leandroid.system.rentacarmanagement.model

data class Color(
    val id: String,
    val name: String
){
    constructor(): this("", "")

    override fun toString(): String {
        return name
    }
}