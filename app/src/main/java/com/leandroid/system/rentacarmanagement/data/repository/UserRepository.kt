package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<Response<ApiResponse<MutableList<User>>>>
    fun getUser(id: String): Flow<Response<ApiResponse<User>>>
    fun saveUser(user: User): Flow<Response<ApiResponse<MutableList<User>>>>
    fun updateUser(user: User): Flow<Response<ApiResponse<MutableList<User>>>>
    fun deleteUser(id: String): Flow<Response<ApiResponse<MutableList<User>>>>
}