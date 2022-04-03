package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.api.adapter.NetworkResponse
import com.leandroid.system.rentacarmanagement.data.datasource.LoginDataSource
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.data.api.utils.NetworkException
import com.leandroid.system.rentacarmanagement.data.api.utils.UnknownException
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(private val dataSource: LoginDataSource) : LoginRepository {

    override fun doLogin(email: String, pass: String): Flow<Response<ApiResponse<User>>> =
        flow {
            try {
                dataSource.doLogin(email, pass).let {
                    when(it){
                        is NetworkResponse.Success -> {
                            emit(Response.Success(it.body))
                        }
                        is NetworkResponse.ApiError -> {
                            emit(Response.Error(Exception(it.body.message)))
                        }
                        is NetworkResponse.NetworkError -> {
                            emit(Response.Error(NetworkException()))
                        }
                        is NetworkResponse.UnknownError -> {
                            emit(Response.Error(UnknownException()))
                        }
                    }
                }
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }

    override fun saveUser(user: User): Flow<Boolean> = flow {
        try {
            val saved = dataSource.saveUser(user)
            emit(saved)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(false)
        }
    }

    override fun saveToken(token: String): Flow<Boolean> = flow {
        try {
            val saved = dataSource.saveToken(token)
            emit(saved)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(false)
        }
    }
}