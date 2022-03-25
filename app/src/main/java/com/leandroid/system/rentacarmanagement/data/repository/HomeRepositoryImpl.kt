package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.datasource.HomeDataSource
import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(private val dataSource: HomeDataSource) : HomeRepository {
    override suspend fun getUser(): Flow<User?> {
      val response = dataSource.getUser()
      return response
    }
}