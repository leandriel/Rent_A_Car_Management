package com.leandroid.system.rentacarmanagement.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl.PreferencesKeys.TOKEN_KEY
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl.PreferencesKeys.USER_KEY
import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class SharedPreferencesImpl(private val context: Context) : SharedPreferences {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FILE_NAME)

    // Gson().fromJson(cityJson, CityModel::class.java)
    override suspend fun saveUser(user: User): Boolean {
        return try {
            val userJson = Gson().toJson(user)
            getDataStore().edit { data ->
                data[USER_KEY] = userJson
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun saveToken(token: String): Boolean {
        return try {
            getDataStore().edit { data ->
                data[TOKEN_KEY] = token
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getUser(): Flow<String> {
        return getDataStore().data
            .map { preferences ->
                preferences[USER_KEY] ?: ""
            }
    }

    override suspend fun getToken(): Flow<String> {
        return getDataStore().data
            .map { preferences ->
                preferences[TOKEN_KEY] ?: ""
            }
    }

    private fun getDataStore() = context.dataStore

    private object PreferencesKeys {
        val USER_KEY = stringPreferencesKey(USER)
        val TOKEN_KEY = stringPreferencesKey(TOKEN)
    }

    companion object {
        private const val USER = "user"
        private const val TOKEN = "token"
        private const val FILE_NAME = "rent_a_car_data"
    }
}