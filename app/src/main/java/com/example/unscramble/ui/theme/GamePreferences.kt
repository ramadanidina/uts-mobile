package com.example.unscramble.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "game_prefs")

class GamePreferences(private val context: Context) {

    companion object {
        val HISTORY_KEY = stringPreferencesKey("history")
    }

    suspend fun saveCorrectAnswer(word: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[HISTORY_KEY] ?: ""
            val updated = if (current.isEmpty()) word else "$current,$word"
            prefs[HISTORY_KEY] = updated
        }
    }

    val historyFlow: Flow<List<String>> = context.dataStore.data.map { prefs ->
        prefs[HISTORY_KEY]?.split(",") ?: emptyList()
    }
}