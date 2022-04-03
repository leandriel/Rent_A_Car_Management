package com.leandroid.system.rentacarmanagement.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserType(
    @Json(name = "_id")
    val id: String,
    val code: Int,
    val name: String
) {
    constructor() : this("", -1, "")
}