package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.model.User

interface UserDataSource {
   fun getUsers(): ApiResponse<MutableList<User>>
   fun getUser(id: String): ApiResponse<User>
   fun saveUser(car: User): ApiResponse<MutableList<User>>
   fun updateUser(car: User): ApiResponse<MutableList<User>>
   fun deleteUser(id: String): ApiResponse<MutableList<User>>
}