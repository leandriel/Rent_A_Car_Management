package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getUser(): Flow<User?>
}