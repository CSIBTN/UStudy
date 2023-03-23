package com.example.ustudy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Query("SELECT * FROM task WHERE day = :day AND year = :year AND month = :month")
    fun getTasks(day: Int, month: String, year: Int): Flow<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}