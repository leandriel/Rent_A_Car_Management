package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.model.*

class LoginDataSourceImpl : LoginDataSource {
    override fun doLogin(email: String, pass: String): ApiResponse<User> {
        return ApiResponse(
            200,
            true,
            "Usuario",
            User(
                "1",
                "l.v.bass@hotmail.com",
                UserType("1", 1, " Admin"),
                Company(" 1", " Rent a car 1", "sss"),
                true,
                " ",
                " ",
                " "
            )
        )
    }
}