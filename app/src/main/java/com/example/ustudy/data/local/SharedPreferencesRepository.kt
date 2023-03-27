package com.example.ustudy.data.local

import kotlinx.coroutines.flow.Flow

interface SharedPreferencesRepository {
    suspend fun setNewAmountOfDaysStudied()
    suspend fun setNewDaysStudiedInARow(isStreakBroken: Boolean)
    suspend fun setNewCurrentDate()
    suspend fun getStreak(): Flow<Int>
    suspend fun getDaysLearned(): Flow<Int>
}