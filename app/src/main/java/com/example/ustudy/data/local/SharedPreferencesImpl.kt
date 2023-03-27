package com.example.ustudy.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class SharedPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SharedPreferencesRepository {

    private val currentDate: Flow<String> = dataStore.data.map {
        it[CURRENT_DAY_KEY] ?: LocalDate.now().toString()
    }
    private val currentLearningDayStreak: Flow<Int> = dataStore.data.map {
        it[DAYS_IN_A_ROW_KEY] ?: 1
    }
    private val daysLearned: Flow<Int> = dataStore.data.map {
        it[DAYS_STUDIED_KEY] ?: 1
    }

    override suspend fun getStreak() = currentLearningDayStreak

    override suspend fun getDaysLearned() = daysLearned

    override suspend fun setNewAmountOfDaysStudied() {
        dataStore.edit { preferences ->
            daysLearned.collect { daysLearned ->
                preferences[DAYS_STUDIED_KEY] = daysLearned + 1
            }
        }
    }

    override suspend fun setNewDaysStudiedInARow(isStreakBroken: Boolean) {
        if (isStreakBroken) {
            dataStore.edit { preferences ->
                preferences[DAYS_IN_A_ROW_KEY] = 0
            }
        } else {
            dataStore.edit { preferences ->
                currentLearningDayStreak.collect { streak ->
                    preferences[DAYS_IN_A_ROW_KEY] = streak + 1
                }
            }
        }
    }

    override suspend fun setNewCurrentDate() {
        currentDate.collect { currentDate ->
            val newCurrentDate = LocalDate.now()
            val currentDateToDate = LocalDate.parse(currentDate)
            if (newCurrentDate != currentDateToDate) {
                when (currentDateToDate.plusDays(1L) == newCurrentDate) {
                    true -> setNewDaysStudiedInARow(false)
                    else -> setNewDaysStudiedInARow(true)
                }
            }
            setNewAmountOfDaysStudied()
        }
    }

    companion object {
        private val CURRENT_DAY_KEY = stringPreferencesKey("CURRENT_DAY_KEY")
        private val DAYS_IN_A_ROW_KEY = intPreferencesKey("DAYS_IN_A_ROW")
        private val DAYS_STUDIED_KEY = intPreferencesKey("DAYS_STUDIED_KEY")
    }
}