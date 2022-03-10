package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun doLogin(email: String, pass: String): Flow<Response<ApiResponse<User>>>
}