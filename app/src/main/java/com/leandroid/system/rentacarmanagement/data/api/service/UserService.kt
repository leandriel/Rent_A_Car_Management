package com.leandroid.system.rentacarmanagement.data.api.service

import com.leandroid.system.rentacarmanagement.data.api.Retrofit
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptor
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.model.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @POST("users")
    fun saveUser(
        @Path("userId") userId: String?,
        @Path("companyId") companyId: String?,
        user: User
    ): ApiResponse<MutableList<User>>

    @PUT("users")
    fun updateUser(
        @Path("userId") userId: String?,
        @Path("companyId") companyId: String?,
        user: User
    ): ApiResponse<MutableList<User>>

    @PUT("users")
    fun deleteUser(
        @Path("userId") userId: String?,
        @Path("companyId") companyId: String?,
        id: String
    ): ApiResponse<MutableList<User>>

    @GET("users")
    fun getUsers(): ApiResponse<MutableList<User>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            baseUrl: String
        ): UserService {
            return Retrofit
                .invoke(connectivityInterceptor, baseUrl)
                .create(UserService::class.java)
        }
    }
}