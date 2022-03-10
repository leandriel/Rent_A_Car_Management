package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.datasource.LoginDataSource
import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(private val dataSource: LoginDataSource) : LoginRepository {

    override fun doLogin(email: String, pass: String): Flow<Response<ApiResponse<User>>> =
        flow {
            try {
                val apiResponse = dataSource.doLogin(email, pass)
                emit(Response.Success(apiResponse))
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }
}