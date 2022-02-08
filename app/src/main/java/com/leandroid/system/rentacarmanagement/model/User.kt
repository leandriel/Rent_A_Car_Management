package com.leandroid.system.rentacarmanagement.model

data class User (
    val id: String,
    val email: String,
    val type: UserType,
    val company: Company,
    val active: Boolean,
    val createDate: String,
    val updateDate: String,
    val deleteDate: String
    )