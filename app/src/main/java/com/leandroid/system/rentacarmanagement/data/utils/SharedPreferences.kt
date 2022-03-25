package com.leandroid.system.rentacarmanagement.data.utils

import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow

interface SharedPreferences {
    suspend fun saveUser(user: User): Boolean
    suspend fun saveToken(token: String) : Boolean
    suspend fun getUser(): Flow<User?>
    suspend fun getToken(): Flow<String>
}