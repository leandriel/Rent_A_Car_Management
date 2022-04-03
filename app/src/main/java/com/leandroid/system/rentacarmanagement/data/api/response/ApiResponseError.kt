package com.leandroid.system.rentacarmanagement.data.api.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponseError(
        val status: String,
        val message: String){
        constructor(): this("status", "Error")
}