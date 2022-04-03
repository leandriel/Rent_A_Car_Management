package com.leandroid.system.rentacarmanagement.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "_id")
    val id: String,
    var userName: String,
    var password: String,
    var type: UserType,
    val company: Company,
    val token: String,
    val isActive: Boolean,
//    val createDate: String,
//    val updateDate: String,
//    val deleteDate: String
) {
    constructor() : this("","", "", UserType(), Company(), "",false)

    val isRequiredEmptyData: Boolean
        get() = userName.isEmpty()



}