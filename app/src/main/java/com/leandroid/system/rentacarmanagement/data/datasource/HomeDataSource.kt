package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {
   suspend fun getUser(): Flow<User?>
}