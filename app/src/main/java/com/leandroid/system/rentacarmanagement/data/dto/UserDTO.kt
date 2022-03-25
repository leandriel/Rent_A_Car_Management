package com.leandroid.system.rentacarmanagement.data.dto

import com.leandroid.system.rentacarmanagement.model.*

data class UserDTO(
    val user: User,
    val types: MutableList<UserType>
) {
    constructor() : this(
        User(),
        mutableListOf()
    )
}