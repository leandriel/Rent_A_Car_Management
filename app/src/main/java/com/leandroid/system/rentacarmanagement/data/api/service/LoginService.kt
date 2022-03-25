package com.leandroid.system.rentacarmanagement.data.api.service

import com.leandroid.system.rentacarmanagement.data.api.Retrofit
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptor
import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.model.User
import retrofit2.http.POST

interface LoginService {
    @POST("auth")
    fun doLogin(email: String, pass: String): ApiResponse<User> //ver token

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