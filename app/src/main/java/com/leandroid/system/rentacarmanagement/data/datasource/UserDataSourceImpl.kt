package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.api.service.UserService
import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferences
import com.leandroid.system.rentacarmanagement.model.*

class UserDataSourceImpl(private val sharedPreferences: SharedPreferences, service: UserService): UserDataSource {
    private val users = mutableListOf<User>(User("1","asd321", "", UserType("1", 1, "admin"), Company("1", "Rent 1", "https://image"),
        "", true, "", "", ""))
   // private val brands = mutableListOf<Brand>(Brand("1", "FORD"), Brand("1", "FIAT"), Brand("1", "HONDA"))
    //private val colors = mutableListOf<Color>(Color("1", "Azul"), Color("3", "Blanco"), Color("2", "Negro"))

    override fun getUsers(): ApiResponse<MutableList<User>> {
        return ApiResponse(200, true, "Usuarios", users)
    }

    override fun getUser(id: String): ApiResponse<User> {
        return ApiResponse(200, true, "Usuarios", users.find { it.id == id } ?: User())
    }

    override fun saveUser(user: User): ApiResponse<MutableList<User>> {
        users.add(user)
        return ApiResponse(200, true, "Usuarios", users)
    }

    override fun updateUser(user: User): ApiResponse<MutableList<User>> {
        users.indexOfFirst {
            it.id == user.id
        }.also {
            users[it] = user
        }

        return ApiResponse(200, true, "Usuarios", users)
    }

    override fun deleteUser(id: String): ApiResponse<MutableList<User>> {
        users.indexOfFirst {
            it.id === id
        }.also {
            users.removeAt(it)
        }
        return ApiResponse(200, true, "Usuarios", users)
    }
}