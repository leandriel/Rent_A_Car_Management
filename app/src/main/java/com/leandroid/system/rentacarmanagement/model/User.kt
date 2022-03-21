package com.leandroid.system.rentacarmanagement.model

data class User(
    val id: String,
    var userName: String,
    var pass: String,
    var type: UserType,
    val company: Company,
    val token: String,
    val active: Boolean,
    val createDate: String,
    val updateDate: String,
    val deleteDate: String
) {
    constructor() : this("","", "", UserType(), Company(), "",false, "", "", "")

    val isRequiredEmptyData: Boolean
        get() = userName.isEmpty()



}