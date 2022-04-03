package com.leandroid.system.rentacarmanagement.data.api.service

import com.leandroid.system.rentacarmanagement.data.api.Retrofit
import com.leandroid.system.rentacarmanagement.data.api.adapter.NetworkResponse
import com.leandroid.system.rentacarmanagement.data.api.request.LoginRequest
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptor
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponseError
import com.leandroid.system.rentacarmanagement.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
    @POST("auth/login")
    suspend fun doLogin(@Body loginRequest: LoginRequest
    ): NetworkResponse<ApiResponse<User>, ApiResponseError>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            baseUrl: String
        ): LoginService {
            return Retrofit
                .invoke(connectivityInterceptor, baseUrl)
                .create(LoginService::class.java)
        }
    }
}