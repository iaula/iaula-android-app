package com.aurelio.baldor.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("auth_prefs")

class AuthPreferences(private val context: Context) {

    companion object {
        val USER_DATA = stringPreferencesKey("user_data")
        val KEY_TOKEN = stringPreferencesKey("auth_token")
        val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val KEY_API_URL = stringPreferencesKey("api_url")
        val KEY_API_PREFIX = stringPreferencesKey("api_prefix")
    }

    val userData: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_DATA]
    }

    suspend fun saveUserData(userData: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_DATA] = userData
        }
    }

    val authToken: Flow<String?> = context.dataStore.data.map { it[KEY_TOKEN] }
    val refreshToken: Flow<String?> = context.dataStore.data.map { it[KEY_REFRESH_TOKEN] }

    suspend fun saveTokens(token: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
        }
    }

    suspend fun saveRefreshToken(refresh_token: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_REFRESH_TOKEN] = refresh_token
        }
    }

    val apiUrl: Flow<String?> = context.dataStore.data.map { it[KEY_API_URL] }
    val apiPrefix: Flow<String?> = context.dataStore.data.map { it[KEY_API_PREFIX] }

    suspend fun saveInstitutionConfig(url: String, prefix: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_API_URL] = url
            prefs[KEY_API_PREFIX] = prefix
        }
    }

    suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
