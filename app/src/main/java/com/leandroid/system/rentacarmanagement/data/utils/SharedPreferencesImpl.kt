package com.leandroid.system.rentacarmanagement.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl.PreferencesKeys.COMPANY_ID_KEY
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl.PreferencesKeys.TOKEN_KEY
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl.PreferencesKeys.USER_ID_KEY
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl.PreferencesKeys.USER_KEY
import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "rent_a_car_data")

class SharedPreferencesImpl(private val context: Context) : SharedPreferences {

    override suspend fun saveUser(user: User): Boolean {
        return try {
            saveUserId(user.id)
            saveCompanyId(user.company.id)
            saveToken(user.token)
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

    override suspend fun saveUserId(id: String): Boolean {
        return try {
            getDataStore().edit { data ->
                data[USER_ID_KEY] = id
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun saveCompanyId(id: String): Boolean {
        return try {
            getDataStore().edit { data ->
                data[COMPANY_ID_KEY] = id
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getUser(): Flow<User?> {
        try {
            val  a =  getDataStore().data
                .map { preferences ->
                    val json = preferences[USER_KEY] ?: ""
                    try {
                        Gson().fromJson(json, User::class.java)
                    } catch (e: JsonSyntaxException ){
                        e.printStackTrace()
                        null
                    }
                }
            return a
        } catch (e: java.lang.Exception){
            print(e)
            return flow {  null }
        }
    }

    override suspend fun getToken(): Flow<String> {
        return getDataStore().data
            .map { preferences ->
                preferences[TOKEN_KEY] ?: ""
            }
    }

    override suspend fun getUserId(): Flow<String> {
        return getDataStore().data
            .map { preferences ->
                preferences[USER_ID_KEY] ?: ""
            }
    }

    override suspend fun getCompanyId(): Flow<String> {
        return getDataStore().data
            .map { preferences ->
                preferences[COMPANY_ID_KEY] ?: ""
            }
    }

    private fun getDataStore() = context.dataStore

    private object PreferencesKeys {
        val USER_KEY = stringPreferencesKey(USER)
        val TOKEN_KEY = stringPreferencesKey(TOKEN)
        val USER_ID_KEY = stringPreferencesKey(USER_ID)
        val COMPANY_ID_KEY = stringPreferencesKey(COMPANY_ID)
    }

    companion object {
        private const val USER = "user"
        private const val TOKEN = "token"
        private const val USER_ID = "user_id"
        private const val COMPANY_ID = "company_id"
        private const val FILE_NAME = "rent_a_car_data"
    }
}