package com.leandroid.system.rentacarmanagement.data.utils

data class ApiResponse<T>(
    val code: Int,
    val success: Boolean,
    val message: String,
    val data: T?
){
    constructor(): this(200, true, "Success", null,)
    constructor(data: T?): this(200, true, "Success", data)
}