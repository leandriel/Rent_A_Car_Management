package com.leandroid.system.rentacarmanagement.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Company(
    @Json(name = "_id")
    val id: String,
    val name: String,
    val urlImage: String?
) {
    constructor() : this("", "", "")
}