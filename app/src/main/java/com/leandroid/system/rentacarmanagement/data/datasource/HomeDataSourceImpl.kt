package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.api.service.HomeService
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferences
import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeDataSourceImpl(private val sharedPreferences: SharedPreferences, service: HomeService): HomeDataSource {
    override suspend fun getUser(): Flow<User?> = sharedPreferences.getUser()

}