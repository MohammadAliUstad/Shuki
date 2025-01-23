package com.yugentech.shuki.data.tasks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Insert
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTask(id: Long): Flow<Task>

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>
}