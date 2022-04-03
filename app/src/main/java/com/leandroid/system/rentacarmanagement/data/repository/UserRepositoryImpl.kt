package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.datasource.UserDataSource
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val dataSource: UserDataSource): UserRepository {
    override fun getUsers(): Flow<Response<ApiResponse<MutableList<User>>>> = flow {
       try {
           val apiResponse = dataSource.getUsers()
           emit(Response.Success(apiResponse))
       } catch (e:Exception){
           emit(Response.Error(e))
       }
    }

    override fun getUser(id: String): Flow<Response<ApiResponse<User>>> = flow {
        try {
            val apiResponse = dataSource.getUser(id)
            emit(Response.Success(apiResponse))
        } catch (e:Exception){
            emit(Response.Error(e))
        }
    }

    override fun saveUser(user: User): Flow<Response<ApiResponse<MutableList<User>>>> = flow {
        try {
            val apiResponse = dataSource.saveUser(user)
            emit(Response.Success(apiResponse))
        } catch (e:Exception){
            emit(Response.Error(e))
        }
    }

    override fun updateUser(user: User): Flow<Response<ApiResponse<MutableList<User>>>> = flow {
        try {
            val apiResponse = dataSource.updateUser(user)
            emit(Response.Success(apiResponse))
        } catch (e:Exception){
            emit(Response.Error(e))
        }
    }

    override fun deleteUser(id: String): Flow<Response<ApiResponse<MutableList<User>>>> = flow {
        try {
            val apiResponse = dataSource.deleteUser(id)
            emit(Response.Success(apiResponse))
        } catch (e:Exception){
            emit(Response.Error(e))
        }
    }
}