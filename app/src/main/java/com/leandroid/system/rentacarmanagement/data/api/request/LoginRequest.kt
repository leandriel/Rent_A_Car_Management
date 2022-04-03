package com.leandroid.system.rentacarmanagement.data.api.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(val userName: String, val password: String){
    constructor():this("", "")
}