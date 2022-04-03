package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.api.adapter.NetworkResponse
import com.leandroid.system.rentacarmanagement.data.api.request.LoginRequest
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponseError
import com.leandroid.system.rentacarmanagement.data.api.service.LoginService
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferences
import com.leandroid.system.rentacarmanagement.model.User

class LoginDataSourceImpl(private val sharedPreferences: SharedPreferences, private val  service: LoginService) : LoginDataSource {
    override suspend fun doLogin(userName: String, password: String): NetworkResponse<ApiResponse<User>, ApiResponseError> {
        return service.doLogin(LoginRequest( userName, password))
    }

    override suspend fun saveToken(token: String): Boolean {
        return sharedPreferences.saveToken(token)
    }

    override suspend fun saveUser(user: User): Boolean {
        return sharedPreferences.saveUser(user)
    }
}