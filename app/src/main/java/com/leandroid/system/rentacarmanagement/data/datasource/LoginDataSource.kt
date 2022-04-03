package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.data.api.adapter.NetworkResponse
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponseError
import com.leandroid.system.rentacarmanagement.model.User

interface LoginDataSource {
   suspend fun doLogin(email: String, pass: String): NetworkResponse<ApiResponse<User>, ApiResponseError>
   suspend fun saveUser(user: User): Boolean
   suspend fun saveToken(token: String): Boolean

}