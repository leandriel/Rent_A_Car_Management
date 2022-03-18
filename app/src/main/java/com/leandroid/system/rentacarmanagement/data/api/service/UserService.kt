package com.leandroid.system.rentacarmanagement.data.api.service

import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.model.User
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    //change pass admin unable for the moment
//createUser
    //updateUser
    //deleteUser
    //activeUser
    //unActiveUser
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
    fun deleteCar(
        @Path("userId") userId: String?,
        @Path("companyId") companyId: String?,
        id: String
    ): ApiResponse<MutableList<User>>

}