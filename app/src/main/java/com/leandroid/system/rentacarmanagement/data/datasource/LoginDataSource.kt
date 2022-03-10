package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.model.User

interface LoginDataSource {
   fun doLogin(email: String, pass: String): ApiResponse<User>
}